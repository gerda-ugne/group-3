package hospital.staff;

import hospital.undo_redo.Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppointmentAction<Staff> implements Action<Staff> {

	private String actionName;
	private Staff staff;
	private Method undo;
	private Object[] undoArgs;
	private Method redo;
	private Object[] redoArgs;

	public AppointmentAction(String actionName, Staff staff, Method undo, Object[] undoArgs, Method redo, Object[] redoArgs) {
		this.actionName = actionName;
		this.staff = staff;
		this.undo = undo;
		this.undoArgs = undoArgs;
		this.redo = redo;
		this.redoArgs = redoArgs;
	}

	@Override
	public void undo() throws UndoNotPossibleException {
		try {
			undo.invoke(staff, undoArgs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new UndoNotPossibleException(e.getMessage());
		}
	}

	@Override
	public void redo() throws RedoNotPossibleException {
		try {
			redo.invoke(staff, redoArgs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RedoNotPossibleException(e.getMessage());
		}
	}

	@Override
	public String getActionName() {
		return this.actionName;
	}
}