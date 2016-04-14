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
	private String result;
	
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
	
	@Override
	public final void dispatch(){
		try{
			this.result = this.login.read(getFilename());
		}
		catch(WrongFileTypeException e){
			e.printStackTrace();
		}
		catch(EntityDoesNotExistException e){
			e.printStackTrace();
		}
	} 

}
