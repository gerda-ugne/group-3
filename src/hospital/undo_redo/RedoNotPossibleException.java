package hospital.undo_redo;

public class RedoNotPossibleException extends Exception {

    public RedoNotPossibleException(String message) {
        super(message);
    }

    public RedoNotPossibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
