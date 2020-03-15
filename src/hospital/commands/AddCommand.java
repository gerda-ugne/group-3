package hospital.commands;

public class AddCommand<T> implements Command<T> {

	private T addedObject;

	public AddCommand(T addedObject) {
		this.addedObject = addedObject;
	}

	@Override
	public void execute() {
		// TODO implement method
	}

	@Override
	public void undo() {
		// TODO implement method
	}

	@Override
	public void redo() {
		// TODO implement method
	}
}