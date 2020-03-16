package hospital.undo_redo;

public interface Action<T> {

	void undo() throws UndoNotPossibleException;

	void redo() throws RedoNotPossibleException;

	String getActionName();

	class UndoNotPossibleException extends Exception {

		public UndoNotPossibleException(String message) {
			super(message);
		}
	}

	class RedoNotPossibleException extends Exception {

		public RedoNotPossibleException(String message) {
			super(message);
		}
	}
}