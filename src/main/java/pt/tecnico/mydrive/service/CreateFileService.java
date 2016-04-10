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

public class CreateFileService extends FileSystemService{

	private long token;	
	private String fileName;
	private String type;	
	private String content;
	private FileSystem fs;
	private User user;
	private Directory workingDir;
	private long id;

	public CreateFileService(long token, String fileName, String type){
		this.token = token;
		this.fileName = fileName;
		this.type = type;
		this.user = this.getLogin(token).getUser();
		this.workingDir = this.getLogin(token).getDirectory();
		this.id = this.getFileSystem().getCounter();
		this.content = "";
	}

	public CreateFileService(long token, String fileName, String type, String content) {
		this(token, fileName, type);
		this.content = content;
	}

	
	
	@Override
	public final void dispatch() throws ContentCannotBeNullException, DirectoryCannotHaveContentException {	

		if (content.length() == 0) {
			if (type.equals("Directory")) {
				new Directory(this.workingDir, this.fileName, this.user, this.id, new DateTime());
			}
			if (type.equals("App")){
				new App(this.workingDir, this.fileName, this.user, this.id, new DateTime() , null);
			}
			if (type.equals("PlainFile")) {
				new PlainFile(this.workingDir, this.fileName, this.user, this.id, new DateTime(), null);
			}
			if (type.equals("Link")){
				throw new ContentCannotBeNullException(content); 
			}
		}
		else {
			if (type.equals("Directory")) {
				throw new DirectoryCannotHaveContentException(content);
			}
			if (type.equals("Link")) {
			   new Link(this.workingDir, this.fileName, this.user, this.id, new DateTime(), content);
		      	}
		      	if (type.equals("App")) { 
			   new App(this.workingDir, this.fileName, this.user, this.id, new DateTime(), content);
		      	}
		      	if(type.equals("PlainFile")) { 
		           new PlainFile(this.workingDir, this.fileName, this.user, this.id, new DateTime(), content);
		       	}	
		     }
	        }
}
