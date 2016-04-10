package pt.tecnico.mydrive.exception;

public class ContentCannotBeNullException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private String content;
	
	public ContentCannotBeNullException(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	@Override
	public String getMessage() {
		return "This content cannot be null.";
	}

}
