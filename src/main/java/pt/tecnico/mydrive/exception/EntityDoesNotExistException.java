package pt.tecnico.mydrive.exception;

public class EntityDoesNotExistException extends MyDriveException {

private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "There is no file or directory with that name in the current directory";
	}
	
}
