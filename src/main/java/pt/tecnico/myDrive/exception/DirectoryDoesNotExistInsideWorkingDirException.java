package pt.tecnico.myDrive.exception;

public class DirectoryDoesNotExistInsideWorkingDirException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String directory;

	public DirectoryDoesNotExistInsideWorkingDirException(String directory) {
		this.directory = directory;
	}

	public String getDirectoryName() {
		return directory;
	}
	
	@Override
	public String getMessage(){
		return "This directory, " +directory + "does not exist inside the working directory.";
	} 
	
	
}
