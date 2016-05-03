package pt.tecnico.mydrive.exception;

public class RootCannotBeRemovedException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	public RootCannotBeRemovedException() {
	}
	
	@Override
	public String getMessage(){
		return "Root cannot be removed";
	} 
}
