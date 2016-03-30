package pt.tecnico.mydrive.exception;

public class DirectoryAlreadyExistsInsideWorkingDirException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private String directory;

	public void DirectoryAlreadyExistInsideWorkingDirException(String directory) {
		this.directory = directory;
	}

	public String getDirectoryName() {
		return directory;
	}
	
	@Override
	public String getMessage(){
		return "This directory, " +directory + "already exists inside the working directory.";
	} 
	
	
}
