package hospital.staff;

import hospital.undo_redo.Action;
import hospital.undo_redo.RedoNotPossibleException;
import hospital.undo_redo.UndoNotPossibleException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an appointment deletion in the hospital booking system. Supports undo and redo actions
 */
public class BookAppointmentAction extends Action {

    // TODO test this too

    /**
     * Base constructor of the class.
     *
     * @param actionName The name of the action this object represents
     * @param staff The staff the deletion took place in
     * @param appointment The deleted appointment
     * @throws NoSuchMethodException If the Staff has no bookAppointment() method with the defined arguments.
     */
    public BookAppointmentAction(String actionName, Staff staff, Appointment appointment)
            throws NoSuchMethodException, IllegalArgumentException {
        super(actionName,
                staff,
                // For undo delete the appointment
                Staff.class.getMethod("deleteAppointment", long.class),
                new Object[]{appointment.getProfessionals().get(0).getId(), appointment.getId()},
                // For redo re-add the appointment (it could have a different ID)
                Staff.class.getMethod("bookAppointment", List.class, LocalDateTime.class, LocalDateTime.class, String.class, TreatmentType.class),
                new Object[]{
                        // Get the required arguments for the bookAppointment() method from the appointment
                        appointment.getProfessionals(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getRoom(),
                        appointment.getTreatmentType()
                });
    }

    /**
     * Undo the deletion of the appointment.
     *
     * @return The re-added appointment (with a new unique ID)
     * @throws RedoNotPossibleException if the undo is not possible.
     */
    @Override
    public Appointment redo() throws RedoNotPossibleException {
        try {
            // Add the appointment again
            Appointment reAddedAppointment = (Appointment) undo.invoke(executor, undoArgs);
            if (reAddedAppointment != null) {
                // Modify the arguments of the redo (deleteAppointment()) with the newly generated appointment ID
                undoArgs[0] = reAddedAppointment.getId();
                return reAddedAppointment;
            } else {
                throw new UndoNotPossibleException("Could not find the undeleted appointment");
            }
        } catch (Exception e) {
            throw new RedoNotPossibleException(e.getMessage(), e);
        }
    }
}