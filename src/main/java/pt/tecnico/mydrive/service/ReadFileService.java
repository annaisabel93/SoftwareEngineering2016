package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;

public class ReadFileService extends FileSystemService{
	
	private long token;
	private Login login;
	private String filename;
	private String result;
	
	public ReadFileService(long token, String filename){
		this.login = getLogin(token);
		this.filename = filename;
		this.token = token;
	}
	
	public String getFilename(){
		return this.filename;
	}
	
	public String getResult(){
		return this.result;
	}
	
	@Override
	public final void dispatch() throws UserHasInvalidPermissionsException, EntityDoesNotExistException, WrongFileTypeException{

		Entity file = this.login.getDirectory().getByName(getFilename());
		this.result = file.read(this.login.getUser());
		
		
	
	} 

}
