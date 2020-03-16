package hospital.undo_redo;

public interface UndoRedoExecutor {

    void undo(Action action);

    void redo(Action action);
}
