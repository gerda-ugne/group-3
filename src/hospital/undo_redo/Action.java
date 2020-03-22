package hospital.undo_redo;

import java.lang.reflect.Method;

/**
 * Represents an undoable/redoable action took in the system.
 */
public class Action {

	/**
	 * The name which represents this action.
	 */
	protected String actionName;

	/**
	 * The object which can undo and redo the action.
	 */
	protected UndoRedoExecutor executor;

	/**
	 * The method to call on the executor for undo.
	 */
	protected Method undo;

	/**
	 * The arguments needed for the undo method.
	 */
	protected Object[] undoArgs;

	/**
	 * The method to call on the executor for redo.
	 */
	protected Method redo;

	/**
	 * The arguments needed for the redo method.
	 */
	protected Object[] redoArgs;

	/**
	 * Default constructor of the class.
	 * Creates and undoable/redoable action in the system.
	 *
	 * @param actionName The name of the action.
	 * @param executor The object which can undo and redo the action.
	 * @param undo The method to call on the executor for undo.
	 * @param undoArgs The arguments needed for the undo method.
	 * @param redo The method to call on the executor for redo.
	 * @param redoArgs The arguments needed for the redo method.
	 */
	public Action(String actionName, UndoRedoExecutor executor, Method undo, Object[] undoArgs, Method redo, Object[] redoArgs) {
		this.actionName = actionName;
		this.executor = executor;
		this.undo = undo;
		this.undoArgs = undoArgs;
		this.redo = redo;
		this.redoArgs = redoArgs;
	}

	/**
	 * Undo the action which executed before.
	 *
	 * @return The object returned from the undo method.
	 * @throws UndoNotPossibleException If the executor have no such method as passed in the constructor,
	 * 									or if any other error occurs.
	 */
	public Object undo() throws UndoNotPossibleException {
		try {
			return undo.invoke(executor, undoArgs);
		} catch (Exception e) {
			throw new UndoNotPossibleException(e.getMessage());
		}
	}

	/**
	 * Do or redo the action, based on if it was undone or not before
	 *
	 * @return The object returned by the redo method.
	 * @throws RedoNotPossibleException If the executor have no such method as passed in the constructor,
	 * 									or if any other error occurs.
	 */
	public Object redo() throws RedoNotPossibleException {
		try {
			return redo.invoke(executor, redoArgs);
		} catch (Exception e) {
			throw new RedoNotPossibleException(e.getMessage());
		}
	}

	/**
	 * Getter of the name which represents this action.
	 *
	 * @return the name which represents this action.
	 */
	public String getActionName() {
		return this.actionName;
	}
}