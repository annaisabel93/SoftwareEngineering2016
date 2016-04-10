package pt.tecnico.mydrive.exception;

public class AppDoesNotExistException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private String app;

	public AppDoesNotExistException(String app) {
		this.app = app;
	}

	public String getAppName() {
		return app;
	}
	
	@Override
	public String getMessage(){
		return "This app, " + app + ", does not exist in this directory.";
	} 
}
