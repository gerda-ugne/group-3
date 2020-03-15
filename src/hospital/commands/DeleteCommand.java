package hospital.commands;

import hospital.staff.Appointment;

public class DeleteCommand<T> implements Command<T> {

	private T deletedObject;

	public DeleteCommand(T objectToDelete) {
		this.deletedObject = objectToDelete;
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