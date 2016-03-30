package pt.tecnico.mydrive.exception;

public class DirectoryDoesNotExistWithinDirectoryException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String directory;

	public DirectoryDoesNotExistWithinDirectoryException(String directory) {
		this.directory = directory;
	}

	public String getDirectoryName() {
		return directory;
	}
	
	@Override
	public String getMessage(){
		return "This directory, " +directory + "does not exist within this directory.";
	} 
	
	
}
