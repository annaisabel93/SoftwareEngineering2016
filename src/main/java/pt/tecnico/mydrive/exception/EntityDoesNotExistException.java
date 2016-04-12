package pt.tecnico.mydrive.exception;

public class EntityDoesNotExistException extends MyDriveException {

private static final long serialVersionUID = 1L;

	private String filename;
	
	public EntityDoesNotExistException(String filename1) {
		this.filename = filename1;
	}

	@Override
	public String getMessage() {
		return "File: " + this.filename +"does not exist within current directory";
	}
	
}
