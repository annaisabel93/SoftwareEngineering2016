package pt.tecnico.mydrive.exception;

public class InvalidPathLenghtException extends MyDriveException{
	
	private static final long serialVersionUID = 1L;

	private int lenght;
	
	public InvalidPathLenghtException(int lenght) {
		this.lenght = lenght;
	}

	@Override
	public String getMessage() {
		return "Path: " + this.lenght +"has invalid size";
	}

}
