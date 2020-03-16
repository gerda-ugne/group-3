package hospital.staff;

import hospital.undo_redo.Action;
import hospital.undo_redo.UndoNotPossibleException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class DeleteAppointmentAction extends Action {

	public DeleteAppointmentAction(String actionName, Staff staff, Object[] argsForReAdd, Object[] argsForReDelete)
			throws NoSuchMethodException {
		super(actionName,
				staff,
				Staff.class.getMethod("bookAppointment", List.class, Date.class, Date.class, String.class, String.class),
				argsForReAdd,
				Staff.class.getMethod("deleteAppointment", long.class, long.class),
				argsForReDelete
		);
	}

	@Override
	public void undo() throws UndoNotPossibleException {
		try {
			Appointment reAddedAppointment = (Appointment) undo.invoke(executor, undoArgs);
			if (reAddedAppointment != null) {
				redoArgs[1] = reAddedAppointment.getId();
			} else {
				throw new UndoNotPossibleException("Could not find the undeleted appointment");
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new UndoNotPossibleException(e.getMessage());
		}
	}
}