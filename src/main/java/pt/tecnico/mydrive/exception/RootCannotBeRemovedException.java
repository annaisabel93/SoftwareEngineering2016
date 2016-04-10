package pt.tecnico.mydrive.exception;

import pt.tecnico.mydrive.domain.Root;

public class RootCannotBeRemovedException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private Root root;

	public RootCannotBeRemovedException(Root root) {
		this.root = root;
	}
	
	@Override
	public String getMessage(){
		return "Root: cannot be removed";
	} 
}
