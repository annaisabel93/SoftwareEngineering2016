package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;


import org.joda.time.DateTime;
//import exceptions

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;


public class CreateFileService extends FileSystemService{

	private long token;	
	private Directory parent;
	private String fileName;
	private long id;
	private DateTime lastModified;
	private String content;
	private String type;

	public CreateFileService(long token, Directory parent, String fileName, long id, DateTime lastModified, String type){
		this.token = token;
		this.parent = parent;	
		this.fileName = fileName;
		this.id = id;
		this.lastModified = lastModified;
		this.type = type;
		this.content = "";
	}

	public CreateFileService(long token, Directory parent, String fileName, long id, DateTime lastModified, String type, String content) {
		this(token, parent, fileName, id, lastModified, type);
		this.content = content;
	}

	
	
	@Override
	public final void dispatch() throws ContentCannotBeNullException {
		User u = getLogin(token).getUser();
		if (content.length() == 0) {
			if (type.equals("Directory")) {
				new Directory(parent, fileName, u, id, lastModified);
			}
			if (type.equals("App")){
				new App(parent, fileName, u, id, lastModified, null);
			}
			if (type.equals("PlainFile")) {
				new PlainFile(parent, fileName, u, id, lastModified, null);
			}
			if (type.equals("Link")){
				throw new ContentCannotBeNullException(content); 
			}
		}
		else {
			if (type.equals("Link")) {
			   new Link(parent, fileName, u, id, lastModified, content);
		      	}
		      	if (type.equals("App")) { 
			   new App(parent, fileName, u, id, lastModified, content);
		      	}
		      	if(type.equals("PlainFile")) { 
		           new PlainFile(parent, fileName, u, id, lastModified, content);
		       	}	
		     }
	        }
}
