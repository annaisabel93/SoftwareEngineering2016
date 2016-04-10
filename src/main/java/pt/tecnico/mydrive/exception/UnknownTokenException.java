package pt.tecnico.mydrive.exception;

public class UnknownTokenException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private long token;

	public UnknownTokenException(long token1) {
		this.token = token1;
	}

	public long getToken() {
		return this.token;
	}
	
	@Override
	public String getMessage(){
		return "Token: " +getToken() + "is not being used by any Login session";
	} 
	
}