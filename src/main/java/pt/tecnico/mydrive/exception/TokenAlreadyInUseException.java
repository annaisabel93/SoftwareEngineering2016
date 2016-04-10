package pt.tecnico.mydrive.exception;

public class TokenAlreadyInUseException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private long token;

	public TokenAlreadyInUseException(long token1) {
		this.token = token1;
	}

	public long getToken() {
		return this.token;
	}
	
	@Override
	public String getMessage(){
		return "Token: " +getToken() + "is already in use by another Login";
	} 
	
}