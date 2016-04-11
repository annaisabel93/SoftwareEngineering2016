package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.DirectoryCannotHaveContentException;
import pt.tecnico.mydrive.exception.UnknownFileTypeException;


public class CreateFileService extends FileSystemService{

	private long token;	
	private String fileName;
	private String type;	
	private String content;
	private FileSystem fs;
	private User user;
	private Directory workingDir;
	private long id;
	private DateTime lastModified;


	public CreateFileService(long token, String fileName, String type){
		this.token = token;
		this.fileName = fileName;
		this.type = type;
		this.user = this.getLogin(token).getUser();
		this.workingDir = this.getLogin(token).getDirectory();
		this.id = this.getFileSystem().getCounter();
		this.lastModified = new DateTime();
		this.content = "";
	}

	public CreateFileService(long token, String fileName, String type, String content) {
		this(token, fileName, type);
		this.content = content;
	}

	
	
	@Override
	public final void dispatch() throws ContentCannotBeNullException, DirectoryCannotHaveContentException, UnknownFileTypeException {	

		if (content.length() == 0) {
			switch(type) {
				case "Directory":
					new Directory(this.workingDir, this.fileName, this.user, this.id, this.lastModified);
				case "App":
					new App(this.workingDir, this.fileName, this.user, this.id, this.lastModified , null);
				case "PlainFile":
					new PlainFile(this.workingDir, this.fileName, this.user, this.id, this.lastModified, null);
				case "Link":
					throw new ContentCannotBeNullException(content); 
				default:
					throw new UnknownFileTypeException(type);
			}
		}
		else {
			switch(type) {
				case "Link":
			   		new Link(this.workingDir, this.fileName, this.user, this.id, this.lastModified, content);
		      		case "App":
			   		new App(this.workingDir, this.fileName, this.user, this.id, this.lastModified, content);
		      		case "PlainFile":
		           		new PlainFile(this.workingDir, this.fileName, this.user, this.id, this.lastModified, content);
		       		case "Directory":
					throw new DirectoryCannotHaveContentException(content);
				default:
					throw new UnknownFileTypeException(type);
			}	
		
	        }

	}
}
