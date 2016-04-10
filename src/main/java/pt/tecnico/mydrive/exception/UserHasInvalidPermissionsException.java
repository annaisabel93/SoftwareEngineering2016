package pt.tecnico.mydrive.exception;

public class UserHasInvalidPermissionsException extends MyDriveException {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "You don't have the right permissions to perform this action.";
	}
	
}
