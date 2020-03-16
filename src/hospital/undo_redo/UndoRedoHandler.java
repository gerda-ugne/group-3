package hospital.undo_redo;

import java.util.Stack;

public class UndoRedoHandler {

    /**
     * The stack of commands executed by the user, for undo feature.
     */
    private Stack<Action> doneActions;

    /**
     * The stack of commands undone by the user, for redo feature.
     */
    private Stack<Action> undoneActions;

    public UndoRedoHandler() {
        doneActions = new Stack<>();
        undoneActions = new Stack<>();
    }

    public void addAction(Action action) {
        if (action != null) {
            doneActions.add(action);
        }
    }

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
