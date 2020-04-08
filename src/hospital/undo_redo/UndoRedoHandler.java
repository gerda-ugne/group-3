package hospital.undo_redo;

import java.util.Stack;

/**
 * This class handles all the undo and redo commands in a system.
 */
public class UndoRedoHandler {

    /**
     * The stack of commands executed by the user, for undo feature.
     */
    private final Stack<Action> doneActions;

    /**
     * The stack of commands undone by the user, for redo feature.
     */
    private final Stack<Action> undoneActions;

    /**
     * Default constructor of the class.
     */
    public UndoRedoHandler() {
        doneActions = new Stack<>();
        undoneActions = new Stack<>();
    }

    /**
     * Registers a new action done in the system.
     * Adds this new action to the top of undoable actions stack.
     * It also clears the redoable action stack, so the actions undone by the time this method is called will be lost.
     *
     * @param action The new action to add to the undoable actions stack
     */
    public void addAction(Action action) {
        if (action != null) {
            doneActions.add(action);
            undoneActions.clear();
        }
    }

    /**
     * Undoes the last action registered in this handler.
     * Adds the action to the top of the redoable actions stack, so it can be redo later.
     * If there is no action to undo, it does nothing.
     *
     * @return the returned object from the method used to redo the action.
     */
    public Object undo() throws UndoNotPossibleException {
        Object returned = null;
        if (!doneActions.isEmpty()) {
            Action lastAction = doneActions.pop();
            returned = lastAction.undo();
            undoneActions.add(lastAction);
            System.out.println(lastAction.actionName + " has been undone.");
        }
        return returned;
    }

    /**
     * Redoes the last undone action registered in this handler.
     * Adds the action to the top of the undoable actions stack.
     *
     * @return the returned object from the method used to redo the action.
     */
    public Object redo() throws RedoNotPossibleException {
        Object returned = null;
        if (!undoneActions.isEmpty()) {
            Action lastUndone = undoneActions.pop();
            returned = lastUndone.redo();
            doneActions.add(lastUndone);
            System.out.println(lastUndone.actionName + " has been redone.");
        }
        return returned;
    }

    /**
     * Clears the whole undo and redo history.
     */
    public void clearHistory() {
        undoneActions.clear();
        doneActions.clear();
    }
}
