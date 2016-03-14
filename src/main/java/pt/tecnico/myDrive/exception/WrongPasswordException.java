package pt.tecnico.myDrive.exception;

public class WrongPasswordException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String password;

	public WrongPasswordException(String password) {
		this.password = password;
	}

	public String getPassword(){
		return password;
	}
	
	@Override
	public String getMessage() {
		return "You have inserted the wrong password. ";
	}

}  
