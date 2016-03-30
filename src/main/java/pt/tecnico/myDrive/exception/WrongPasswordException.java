package pt.tecnico.mydrive.exception;

public class WrongPasswordException extends MyDriveException {

	private static final long serialVersionUID = 1L;


	public WrongPasswordException() {
	}

	@Override
	public String getMessage() {
		return "You have inserted the wrong password. ";
	}

}  
