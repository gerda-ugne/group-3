package hospital.undo_redo;

public class UndoNotPossibleException extends Exception {

    public UndoNotPossibleException(String message) {
        super(message);
    }
}
