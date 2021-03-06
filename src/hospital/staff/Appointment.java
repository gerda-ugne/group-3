package hospital.staff;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an appointment in one or more professional's electronic diary.
 * Stores and sets data about the appointment.
 */
public class Appointment implements Comparable<Appointment>, Serializable {

	/**
	 * Treatment duration in milliseconds.
	 * Right now it's one hour.
	 */
	public static final Duration TREATMENT_DURATION = Duration.ofHours(1);

	/**
	 * Static counter used to generate unique IDs for every appointment.
	 */
	private static long counter = 1;

	/**
	 * The unique ID of the appointment
	 */
	private final long id;

	/**
	 * The time when the appointment starts
	 */
	private LocalDateTime startTime;

	/**
	 * The time when the appointment ends
	 */
	private LocalDateTime endTime;

	/**
	 * The room the appointment takes place in
	 */
	private String room;

	/**
	 * The type of the treatment
	 */
	private TreatmentType treatmentType;

	/**
	 * The professionals who participate in the treatment
	 */
	private List<Professional> professionals;



	/**
	 * Creates an empty appointment without data, in which only the ID is unique.
	 */
	public Appointment() {
		// TODO check for existing ids or make sure that counter is restored too with the appointments (from save)
		this(null, null, "<undefined>",  new ArrayList<>(), null);
	}

	/**
	 * Constructor for the Appointment class.
	 * @param startTime time when the appointment starts
	 * @param endTime time when the appointment ends
	 */
	public Appointment(LocalDateTime startTime, LocalDateTime endTime) {
		this(startTime, endTime, "<undefined>",  new ArrayList<>(), null);
	}

	/**
	 * Constructor for the Appointment class
	 * @param startTime time when the appointment starts
	 * @param endTime time when the appointment ends
	 * @param room room where appointment takes place
	 * @param treatmentType treatment type of the appointment
	 * @param professionals professionals needed for the appointment
	 */
	public Appointment(LocalDateTime startTime, LocalDateTime endTime, String room, List<Professional> professionals, TreatmentType treatmentType) {
		this.id = counter++;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		this.treatmentType = treatmentType;
		this.professionals = professionals;
	}

	/**
	 * Getter of the ID of the appointment.
	 *
	 * @return the unique ID of the appointment.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter of the starting time of the appointment
	 *
	 * @return the starting time of the appointment
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * Set time of the appointment when it starts
	 *
	 * @param startTime the time to set as starting time to the appointment
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * The getter of the ending time of the appointment
	 *
	 * @return the ending time of the appointment
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the time of the appointment when it ends
	 *
	 * @param endTime The time to set as ending time to the appointment.
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * The getter of the room, where the appointment take place
	 *
	 * @return the room where the appointment takes place
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * Sets the room where the appointment takes place
	 *
	 * @param room the room where the appointment takes place
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * The getter of the appointment's treatment type.
	 *
	 * @return the appointment's treatment type.
	 */
	public TreatmentType getTreatmentType() {
		return treatmentType;
	}

	/**
	 * Sets the type of treatment of the appointment
	 *
	 * @param treatmentType the type of treatment to set to the appointment
	 */
	public void setTreatmentType(TreatmentType treatmentType) {
		this.treatmentType = treatmentType;
	}

	/**
	 * Getter of the professionals who participate in the treatment
	 *
	 * @return the list of professionals who participate in the treatment
	 */
	public List<Professional> getProfessionals() {
		return professionals;
	}


	/**
	 * Sets the professionals who participate in the treatment
	 *
	 * @param professionals the list of professionals who participate in the treatment
	 */
	public void setProfessionals(List<Professional> professionals) {
		this.professionals = professionals;
	}

    /**
     *
     * @param o The other appointment to compare this appointment to
     * @return  0 if the two appointment start at the same time,
     *          a negative number if the argument starts later then this appointment,
     *          a positive number if the argument starts sooner then this appointment
     */
	@Override
	public int compareTo(Appointment o) {
		return o.getStartTime().compareTo(getStartTime());
	}


	/**
	 * Formats the appointment details to make it easy to write
	 * @return a formatted string with the Appointment details
	 */
	@Override
	public String toString() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu HH");
		return "Appointment{" +
				"startTime=" + startTime.format(dateFormat) +
				", endTime=" + endTime.format(dateFormat) +
				", room='" + room + '\'' +
				", treatmentType='" + treatmentType + '\'' +
				", professionals: " + professionals.toString() +
				'}';
	}
}