package pt.tecnico.mydrive.exception;

public class InvalidUsernameException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private String username;

	public InvalidUsernameException(String username){
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getMessage() {
		return "Username " + username + " has to have more than 3 characters";
	}
}