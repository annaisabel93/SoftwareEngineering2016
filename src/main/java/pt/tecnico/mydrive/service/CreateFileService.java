package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.User;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.DirectoryCannotHaveContentException;
import pt.tecnico.mydrive.exception.UnknownFileTypeException;


public class CreateFileService extends FileSystemService{

	private long token;	
	private String fileName;
	private String type;	
	
	private FileSystem fs;
	private Directory workingDir;
	private User user;
	private DateTime lastModified;
	private String content;


	public CreateFileService(long token, String fileName, String type){
		this.token = token;
		this.fileName = fileName;
		this.type = type;
		this.user = this.getLogin(token).getUser();
		this.workingDir = this.getLogin(token).getDirectory();
		this.lastModified = new DateTime();
		this.content = "";
	}

	public CreateFileService(long token, String fileName, String type, String content) {
		this(token, fileName, type);
		this.content = content;
	}

	private int incCounter(){
		FileSystem fs = this.user.getSystem();
		int count = fs.getCounter();
		fs.setCounter( count++ );
		return count;
	}

	private void createCaseContent(boolean isContentNull) {
		switch(this.type) {
			case "Directory":
				if (isContentNull) {
					new Directory(this.workingDir, this.fileName, this.user, incCounter(), this.lastModified);
					break;
				}else{ throw new DirectoryCannotHaveContentException(content); }
			case "App":
				if (isContentNull) { this.content = null; }
				new App(this.workingDir, this.fileName, this.user, incCounter(), this.lastModified, content);
				break;

			case "PlainFile":
				if (isContentNull) { this.content = null; }
				new PlainFile(this.workingDir, this.fileName, this.user, incCounter(), this.lastModified, content);
				break;
			case "Link":
				if (isContentNull) { throw new ContentCannotBeNullException(content); }
				else{
					new Link(this.workingDir, this.fileName, this.user, incCounter(), this.lastModified, content);
					break;

				}
			default:
				throw new UnknownFileTypeException(type);
		}
	}

	
	//verify if receives path or not	
	@Override
	public final void dispatch() throws ContentCannotBeNullException, DirectoryCannotHaveContentException, UnknownFileTypeException {	

		if (content.length() == 0) { createCaseContent(true); }
		else { createCaseContent(false); }

	}

}
