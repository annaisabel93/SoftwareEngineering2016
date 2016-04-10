package pt.tecnico.mydrive.exception;

public class LinkDoesNotExistException extends MyDriveException{

	private static final long serialVersionUID = 1L;

	private String link;

	public LinkDoesNotExistException(String link) {
		this.link = link;
	}

	public String getLinkName() {
		return link;
	}

	@Override
	public String getMessage(){
		return "This link, " + link + ", does not exist in this directory.";
	} 
}
