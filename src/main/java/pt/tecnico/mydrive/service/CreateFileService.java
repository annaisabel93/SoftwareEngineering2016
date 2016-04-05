package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;


import org.joda.time.DateTime;
//import exceptions

public class CreateFileService extends FileSystemService{
	
	private Directory parent;
	private String fileName;
	private User owner;
	private long id;
	private DateTime lastModified;
	private String content;

	public CreateFileService(Directory parent, String fileName, User owner, long id, DateTime lastModified){
		this.parent = parent;	
		this.fileName = fileName;
		this.owner = owner;
		this.id = id;
		this.lastModified = lastModified;
		this.content = "";
	}

	public CreateFileService(Directory parent, String fileName, User owner, long id, DateTime lastModified, String content){
		this(parent,fileName,owner,id,lastModified);
		this.content = content;
	}

	
	
	@Override
	public final void dispatch(){
		if (content.length() == 0) {
			new Directory(parent,fileName,owner,id,lastModified);
		}
		else {
			/*if (getFile(fileName) instanceof App)
				new App(parent,fileName,owner,id,lastModified,content);
			if (getFile(fileName) instanceof Link)
				return;
				new Link();
			if (getFile(fileName) instanceof PlainFile)
				return;
				new PlainFile();*/
		}
	}
}
