package hospital.undo_redo;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Represents an undoable/redoable action took in the system.
 */
public class Action {

	/**
	 * The name which represents this action.
	 */
	protected final String actionName;

	/**
	 * The object which can undo and redo the action.
	 */
	protected final UndoRedoExecutor executor;

	/**
	 * The method to call on the executor for undo.
	 */
	protected final Method undo;

	/**
	 * The arguments needed for the undo method.
	 */
	protected final Object[] undoArgs;

	/**
	 * The method to call on the executor for redo.
	 */
	protected final Method redo;

	/**
	 * The arguments needed for the redo method.
	 */
	protected final Object[] redoArgs;

	/**
	 * Default constructor of the class.
	 * Creates and undoable/redoable action in the system.
	 *
	 * @param actionName The name of the action.
	 * @param executor The object which can undo and redo the action.
	 * @param undo The method to call on the executor for undo. It has to be public.
	 * @param undoArgs The arguments needed for the undo method.
	 * @param redo The method to call on the executor for redo. It has to be public.
	 * @param redoArgs The arguments needed for the redo method.
	 * @throws IllegalArgumentException if the executor doesn't have a method like the undo with the undoArgs as possible parameters
	 * 									or if the executor doesn't have a method like the redo with the redoArgs as possible parameters.
	 */
	public Action(String actionName, UndoRedoExecutor executor, Method undo, Object[] undoArgs, Method redo, Object[] redoArgs) {
		this.actionName = actionName;
		this.executor = executor;
		this.undo = undo;
		this.undoArgs = undoArgs;
		this.redo = redo;
		this.redoArgs = redoArgs;
		String errorMessage = "The executor has no method given as the undo method and with the given arguments";
		// Check if the undo method and its undoArgs are correct
		try {
			Class<?>[] undoArgTypes = undo.getParameterTypes();
			// Try to find the undo method
			executor.getClass().getMethod(undo.getName(), undoArgTypes);
			// Check the arguments
			if (!Arrays.equals(undoArgTypes, Arrays.stream(undoArgs).map(Object::getClass).toArray())) {
				throw new IllegalArgumentException(errorMessage);
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(errorMessage);
		}
		errorMessage = "The executor has no method given as the redo method and with the given arguments";
		// Check if the redo method and its redoArgs are correct
		try {
			Class<?>[] redoArgTypes = redo.getParameterTypes();
			// Try to find the redo method
			executor.getClass().getMethod(redo.getName(), redoArgTypes);
			// Check the arguments
			if (!Arrays.equals(redoArgTypes, Arrays.stream(redoArgs).map(Object::getClass).toArray())) {
				throw new IllegalArgumentException(errorMessage);
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * Undo the action which executed before.
	 *
	 * @return The object returned from the undo method.
	 * @throws UndoNotPossibleException If some error occurs during executing the undo method.
	 */
	public Object undo() throws UndoNotPossibleException {
		try {
			return undo.invoke(executor, undoArgs);
		} catch (Exception e) {
			throw new UndoNotPossibleException(e.getMessage(), e);
		}
	}

	/**
	 * Do or redo the action, based on if it was undone or not before
	 *
	 * @return The object returned by the redo method.
	 * @throws RedoNotPossibleException If some error occurs during executing the redo method.
	 */
	public Object redo() throws RedoNotPossibleException {
		try {
			return redo.invoke(executor, redoArgs);
		} catch (Exception e) {
			throw new RedoNotPossibleException(e.getMessage(), e);
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