package pt.tecnico.mydrive.exception;

public class UnknownFileTypeException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private String type;
	
	public UnknownFileTypeException(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String getMessage() {
		return "The type that has been chosen has parameter: " + this.getType() + " is not a valid type.";
	}

}
