package hospital.undo_redo;

import java.util.Stack;

/**
 * This class handles all the undo and redo commands in a system.
 */
public class UndoRedoHandler {

    /**
     * The stack of commands executed by the user, for undo feature.
     */
    private Stack<Action> doneActions;

    /**
     * The stack of commands undone by the user, for redo feature.
     */
    private Stack<Action> undoneActions;

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
     */
    public void undo() {
        if (!doneActions.isEmpty()) {
            Action lastAction = doneActions.pop();
            try {
                lastAction.undo();
                undoneActions.add(lastAction);
            } catch (UndoNotPossibleException e) {
                // TODO handle exception
            }
        }
    }

    /**
     * Redoes the last undone action registered in this handler.
     * Adds the action to the top of the undoable actions stack.
     */
    public void redo() {
        if (!undoneActions.isEmpty()) {
            Action lastUndone = undoneActions.pop();
            try {
                lastUndone.redo();
                doneActions.add(lastUndone);
            } catch (RedoNotPossibleException e) {
                // TODO handle exception
            }
        }
    }
}
