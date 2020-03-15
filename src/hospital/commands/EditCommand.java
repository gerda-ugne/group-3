package hospital.commands;

import hospital.staff.Appointment;

public class EditCommand<T> implements Command<T> {

	private T objectBefore;

	public EditCommand(T objectBefore) {
		this.objectBefore = objectBefore;
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