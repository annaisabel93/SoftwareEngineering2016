package pt.tecnico.mydrive.exception;

public class VariableDoesNotExistException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "This Variable is not valid";
	}
}
