package hospital.staff;

import hospital.undo_redo.UndoRedoExecutor;
import hospital.timeLogger.TimeLogger;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Staff class contains a HashSet of Professionals.
 * The HashSet implements the Set interface.
 *
 * Members can be added and removed from the staff.
 *
 */
public class Staff implements UndoRedoExecutor, Serializable {

	private static final Map<DayOfWeek, WorkingHours> defaultWorkingHours = new HashMap<>(7);
	static {
		for (DayOfWeek day : DayOfWeek.values()) {
			defaultWorkingHours.put(day, new WorkingHours(day, LocalTime.of(8, 0), LocalTime.of(16, 0)));
		}
	}

	/**
	 * The set of professionals the staff is consists of.
	 */
	private Set<Professional> staff;

	/**
	 * The admin of the system.
	 */
	private Administrator admin;

	/**
	 * All of the appointments
	 */
	private Map<Long, Appointment> appointments;

	/**
	 * Constructor for Staff class
	 */
	public Staff() {
		staff = new HashSet<>();
		admin = new Administrator("Admin", "Chief", "Heaven");
		appointments = new HashMap<>();
		if (staff.isEmpty()) {
			for (Role role : Role.values()) {
				for (int i = 0; i < 5; i++) {
					Professional professional = new Professional(role.toString(), String.valueOf(i), role, role.toString() + "Office" + i);
					professional.setWorkingHours(defaultWorkingHours);
					staff.add(professional);
				}
			}
		}
	}

	/**
	 * Adds a new member to the staff.
	 *
	 * @param newMember The professional to add as a new member.
	 */
	public void addMember(Professional newMember) {

		staff.add(newMember);
	}

	/**
	 * Removes a professional from the staff, also removing them from each of their appointments
	 * @param member member to remove
	 * @return false/true whether the member was removed
	 */
	public boolean removeMember(Professional member) {

		ElectronicDiary diary = member.getDiary();
		List<Appointment> appList = diary.getAppointments();
		Role role = member.getRole();
		List<Professional> eligibleProfs = new ArrayList<>();
		for(Professional prof : staff)
		{
			if(prof.getRole().equals(role)) eligibleProfs.add(prof);
		}
		for(Appointment app : appList)
		{
			List<Professional> appProfessionals=app.getProfessionals();
			appProfessionals.remove(member);
			for(Professional p : eligibleProfs)
			{
				if(!p.searchAvailability(app.getStartTime(),app.getEndTime()).isEmpty())
				{
					appProfessionals.add(p);
					break;
				}
			}
			if(appProfessionals.isEmpty()) deleteAppointment(member.getId(),app.getId());
			else app.setProfessionals(appProfessionals);
		}
		return staff.remove(member);
	}

	/**
	 * Finds all common available slots in a list of provided professionals.
	 * Time taken to search the diaries is recorded and displayed to the user.
	 *
	 * @param professionals The list of professionals who have to share the new appointment.
	 * @param from The starting time of the interval to search in.
	 * @param to The ending time of the interval to search in.
	 * @return A set of available time-slots as empty appointments, which are free for all of the involved professionals.
	 */
	public List<Appointment> searchAvailability(List<Professional> professionals, LocalDateTime from, LocalDateTime to) {

		// TODO move it into Menu
		//Records current time to calculate time taken to search availability
		TimeLogger logTime = new TimeLogger("search for available time slots");

		//Local variable for holding personal appointments of one professional at a time
		List<List<Appointment>> personalFreeSlots = new ArrayList<>();
		Set <Appointment> allAppointments = new TreeSet<>(Appointment::compareTo);

		//Professional availability is retrieved and recorded into a set
		for (Professional professional:
			 professionals) {
			if (professional == null) return new ArrayList<>(0);
			List<Appointment> tempList = professional.searchAvailability(from, to);
			personalFreeSlots.add(tempList);
			allAppointments.addAll(tempList);
		}

		// Get the intersection of all the lists
		for (List<Appointment> list : personalFreeSlots) {
			allAppointments = allAppointments.stream() // Go through all of the appointments
					.filter(appointment -> list.stream() // Go through one of the personalFreeSlots
							.filter(freeSlot -> freeSlot.compareTo(appointment) == 0) // Filter for the appointments which are in both lists
							.findAny() // If there is any, return it
							.orElse(null) // If there isn't, return null
							!= null) // If the returned object is not null, the appointment is in both list, leave it in allAppointments
					.collect(Collectors.toSet());
		}

		//Converts set into a list type object
		List<Appointment> listOfAppointments = new ArrayList<>(allAppointments);
		//Sorts the list by start date
		listOfAppointments.sort(Comparator.comparing(Appointment::getStartTime));

		// TODO move it into menu
		//Time is logged at the end of the method
		logTime.calculateElapsedTime();

		return listOfAppointments;
	}

	/**
	 * List of professionals is filtered by role.
	 *
	 * @param role role to filter by
	 * @return list of professionals only with the given role
	 */
	public List<Professional> getProfessionalsByRole(Role role)
	{

		return staff.stream()
				.filter(professional -> professional.getRole().equals(role))
				.collect(Collectors.toList());

	}

	/**
	 * Books an appointment in one or more electronic diaries of the involved professionals.
	 * It also checks if the given time-slot is free and available for all of the involved professionals.
	 *
	 * @param professionals A list of the ids of the professionals who are involved in the new appointment.
	 * @param startTime The time when the new appointment starts.
	 * @param endTime The time when the new appointment ends.
	 * @param room The name/number of the room where the appointment will take place.
	 * @param treatmentType The type of treatment the new appointment has.
	 * @return The newly created appointment or null if the booking was unsuccessful.
	 */
	public Appointment bookAppointment(List<Professional> professionals, LocalDateTime startTime, LocalDateTime endTime, String room, TreatmentType treatmentType) {

		//creates new appointment instance with the given parameters
		Appointment newAppointment = new Appointment(startTime, endTime, room, professionals, treatmentType);

		//creates new appointment list of available slots at the given time for all involved professionals, should only have one list item
		List<Appointment> freeSlots = searchAvailability(professionals, startTime, endTime);

		//if the list of free slots isn't empty (all involved professionals have one free slot at the given time), adds the appointment to all of their diaries
		if(!freeSlots.isEmpty())
		{
			for(Professional professional: professionals)
			{
				professional.addAppointment(newAppointment);
			}
			appointments.put(newAppointment.getId(), newAppointment);
			return newAppointment;
		}
		//if at least one of the professionals don't have a free slot at the given time, return null
		return null;
	}

	/**
	 * Edit one of the appointment of one of the staff member.
	 * If more than one professionals are involved in the treatment,
	 * it checks if the modifications do not conflict with any of the professionals' electronic diary.
	 *
	 * @param appointmentId The ID of the appointment to modify.
	 * @param newProfessionals A list of the professionals who are involved in the appointment, including the owner themself.
	 * @param startTime The time when the appointment starts.
	 * @param endTime The time when the appointment ends.
	 * @param room The name/number of the room where the appointment will take place.
	 * @return	The modified appointment.
	 * 			It is possible, that the modification was unsuccessful, and the returned appointment is the unmodified one.
	 * 			The return can be null, if the appointment could not have been found.
	 */
	public Appointment editAppointment(long appointmentId, List<Professional> newProfessionals, LocalDateTime startTime, LocalDateTime endTime, String room) {
		List<Professional> oldProfessionals;

		Appointment appointmentToChange = appointments.get(appointmentId);

		if(appointmentToChange != null) {
			oldProfessionals = appointmentToChange.getProfessionals();

			//check if all professionals have the free slot at the required time
			List<Appointment> freeSlot = searchAvailability(newProfessionals, startTime, endTime);


			//if they do, edit appointment's fields
			//and remove appointment from the old professionals' diaries
			//and add it to the new ones'
			if (!freeSlot.isEmpty()) {
				for (Professional oldProfessional : oldProfessionals) {
					oldProfessional.deleteAppointment(appointmentId);
				}

				appointmentToChange.setStartTime(startTime);
				appointmentToChange.setEndTime(endTime);
				appointmentToChange.setProfessionals(newProfessionals);
				appointmentToChange.setRoom(room);

				for (Professional newProfessional : newProfessionals) {
					newProfessional.addAppointment(appointmentToChange);
				}
			}
		}
		return appointmentToChange;
	}

	/**
	 * Deletes an appointment from one of professional's electronic diary.
	 * It also deletes it from all the involved professionals' diaries.
	 *
	 * @param professionalId The ID of the professional who has the appointment.
	 * @param appointmentId The ID of the appointment to delete.
	 * @return The deleted appointment or null, if the deletion was unsuccessful.
	 */
	public Appointment deleteAppointment(long professionalId, long appointmentId) {
		// TODO if have time make it faster
		Appointment deletedAppointment=null;
		boolean appointmentFound=false;
		for (Professional professional: staff)
			{
				if(professional.getDiary().getAppointment(appointmentId)!=null)
				{
					if(!appointmentFound)
					{
						appointmentFound = true;
						deletedAppointment = professional.getDiary().getAppointment(appointmentId);
					}
					professional.deleteAppointment(appointmentId);
				}
			}
		appointments.remove(Objects.requireNonNull(deletedAppointment).getId());
		return deletedAppointment;
	}

	/**
	 * Search for an appointment in one of the professional's electronic diary.
	 *
	 * @param appointmentId The ID of the appointment to search for.
	 * @return The found appointment or null if it could not have been found.
	 */
	public Appointment searchAppointment(long appointmentId) {

		return appointments.get(appointmentId);
	}

	/**
	 * Returns the set of all the professionals
	 * @return set of professionals
	 */
	public Set<Professional> getStaff() {
		return staff;
	}

	/**
	 * Sets the staff of professionals
	 * @param staff professional set to be set
	 */
	public void setStaff(Set<Professional> staff) {
		this.staff = staff;
	}

	/**
	 * Searches professional by ID and returns if found
	 * @param id id to be filtered by
	 * @return the found professional or null if not found
	 */
	public Professional searchById(String id)
	{
		String idAsString;
		for (Professional professional:
		staff) {

			//Converts the id into string for comparison
			idAsString = String.valueOf(professional.getId());
			if(idAsString.equals(id)) return professional;
		}

		return null;
	}

	/**
	 * Searches professional by their username and returns if found
	 * @param username username to be filtered by
	 * @return if found professional, otherwise null
	 */
	public Professional searchByUsername(String username)
	{
		for(Professional professional: staff)
		{
			if(professional.getUsername().equals(username)) return professional;
		}

		return null;
	}

	/**
	 * Getter method for the administrator
	 * @return administrator
	 */
	public Administrator getAdmin() {
		return admin;
	}

	/**
	 * Setter method for the administrator
	 * @param admin administrator to be set
	 */
	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}
}