package hospital.commands;

public interface Command<T> {

	void execute();

	void undo();

	void redo();

}