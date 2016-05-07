package pt.tecnico.mydrive.service;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.DirectoryCannotHaveContentException;
import pt.tecnico.mydrive.exception.UnknownFileTypeException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.UsernameAlreadyExistsException;
import pt.tecnico.mydrive.exception.InexistentPointerForLinkException;

public class CreateFileService extends FileSystemService{

	private long token;	
	private String fileName;
	private String type;
	
	private Directory workingDir;
	private User user;
	private DateTime lastModified;
	private String content;


	public CreateFileService(long token, String fileName, String type, String content){
		this.token = token;
		this.fileName = fileName;
		this.type = type;
		this.user = this.getLogin(token).getUser();
		this.workingDir = this.getLogin(token).getDirectory();
		this.lastModified = new DateTime();
		this.content = content;
	}


	private void createCaseContent() throws UnknownFileTypeException, DirectoryCannotHaveContentException, UserHasInvalidPermissionsException, InexistentPointerForLinkException {
		this.user.createFile(token, fileName, lastModified, content, type);
	}

		
	@Override
	public final void dispatch(){	
		this.createCaseContent();
	}

}
