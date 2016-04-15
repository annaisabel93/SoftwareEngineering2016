package pt.tecnico.mydrive.exception;

public class WrongFileTypeException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "The file isn't a text file.";
	}
}
