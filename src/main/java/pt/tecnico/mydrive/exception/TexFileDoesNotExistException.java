package pt.tecnico.mydrive.exception;

public class TexFileDoesNotExistException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String filename;

	public TexFileDoesNotExistException(String name) {
		this.filename = name;
	}

	public String getDirectoryName() {
		return filename;
	}
	
	@Override
	public String getMessage(){
		return "This file, " +filename + "does not exist inside the working directory.";
	} 
	
	
}
