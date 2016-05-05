package pt.tecnico.mydrive.exception;

public class InexistentPointerForLinkException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private String inexistentReceivedDirName;

	public InexistentPointerForLinkException (String inexistentReceivedDirName) {
		this.inexistentReceivedDirName =  inexistentReceivedDirName;
	}

	//In case of being inexistent it will return null
	@Override
	public String getMessage() {
		return "The link path that was trying to be created, points at a Directory which is inexistent -> null.";
	}

}
