package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;

public class ReadFileService extends FileSystemService{
	
	private long token;
	private Login login;
	private String filename;
	
	public ReadFileService(long token, String filename){
		this.login = getLogin(token);
		this.filename = filename;
		this.token = token;
		checkPermissions(login.getUser().getMask());
	}
	
	public long getToken(){
		return this.token;
	}
	
	public String getFilename(){
		return this.filename;
	}
	
	public void checkPermissions(byte[] permissions) throws UserHasInvalidPermissionsException {
		if(permissions[0] == 0) {
			throw new UserHasInvalidPermissionsException();
		}
		
	}
	
	public String returnContent(String filename) throws WrongFileTypeException{
		User user = this.login.getUser();
		Entity file = this.login.getDirectory().getByName(filename); 
		if(file == null){
			throw new EntityDoesNotExistException(filename);
		}
		if(!(file instanceof PlainFile)){  //verificacao fica ou sai?
			throw new WrongFileTypeException();
		}
		
		return ((PlainFile) file).getContent();	
	}
	
	@Override
	public final void dispatch(){
		try {
			returnContent(getFilename());
		} catch (WrongFileTypeException e) {
			e.printStackTrace();
		}
	} 

}
