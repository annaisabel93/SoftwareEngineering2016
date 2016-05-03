package pt.tecnico.mydrive.exception;

public class InvalidPasswordException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public InvalidPasswordException(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}

	@Override
	public String getMessage() {
		return "Password " + password + " has to have more than 8 characters";
	}
}