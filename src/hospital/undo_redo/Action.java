package hospital.undo_redo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Action {

	protected String actionName;

	protected UndoRedoExecutor executor;

	protected Method undo;
	protected Object[] undoArgs;
	protected Method redo;
	protected Object[] redoArgs;

	public Action(String actionName, UndoRedoExecutor executor, Method undo, Object[] undoArgs, Method redo, Object[] redoArgs) {
		this.actionName = actionName;
		this.executor = executor;
		this.undo = undo;
		this.undoArgs = undoArgs;
		this.redo = redo;
		this.redoArgs = redoArgs;
	}

	public void undo() throws UndoNotPossibleException {
		try {
			undo.invoke(executor, undoArgs);
		} catch (Exception e) {
			throw new UndoNotPossibleException(e.getMessage());
		}
	}

	public void redo() throws RedoNotPossibleException {
		try {
			redo.invoke(executor, redoArgs);
		} catch (Exception e) {
			throw new RedoNotPossibleException(e.getMessage());
		}
	}

	public String getActionName() {
		return this.actionName;
	}
}