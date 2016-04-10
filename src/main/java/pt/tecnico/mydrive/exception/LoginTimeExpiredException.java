package pt.tecnico.mydrive.exception;

public class LoginTimeExpiredException extends MyDriveException {

	private static final long serialVersionUID = 1L;



	public LoginTimeExpiredException() {
	}
	
	@Override
	public String getMessage(){
		return "Login exceeded the max time(2hours)";
	} 
	
	
}
