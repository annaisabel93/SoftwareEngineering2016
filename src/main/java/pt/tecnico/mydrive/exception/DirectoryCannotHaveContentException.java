package pt.tecnico.mydrive.exception;

public class DirectoryCannotHaveContentException extends MyDriveException{
	private static final long serialVersionUID = 1L;

	private String content;
	
	public DirectoryCannotHaveContentException(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	@Override
	public String getMessage() {
		return "A Directory cannot be created with a content.In this case, the content you are trying to give as parameter " + this.getContent() + " is not valid, for this type of file." ;
	}

}
