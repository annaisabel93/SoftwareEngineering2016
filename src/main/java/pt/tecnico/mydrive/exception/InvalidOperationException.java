package pt.tecnico.mydrive.exception;

public class InvalidOperationException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Cannot execute an app";
	}
}
