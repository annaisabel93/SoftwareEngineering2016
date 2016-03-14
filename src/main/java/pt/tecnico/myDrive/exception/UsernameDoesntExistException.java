package pt.tecnico.myDrive.exception;

public class UsernameDoesntExistException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String username;

	public UsernameDoesntExistException(String username){
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getMessage() {
		return "Username " + username + " does not exist";
	}

} 
