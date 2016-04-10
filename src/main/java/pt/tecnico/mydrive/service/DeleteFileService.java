package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;


public class DeleteFileService extends FileSystemService {
	Login login;
	long token;
	String filename;
	boolean hasDeletePermissions;
	
	
	public DeleteFileService(String filename) throws UnknownTokenException {
		this.login = getLogin(token);
		this.token = this.login.getToken();
		this.filename = filename;
		
		byte[] permissions = login.getUser().getMask();
		this.hasDeletePermissions = false;
		if(permissions[3] == 1) {
			this.hasDeletePermissions = true;
		}
	}
	
	@Override
	protected void dispatch() {
		// TODO Auto-generated method stub
		if(hasDeletePermissions) {
			login.getUser().getEntityByName(filename).delete();	
		}
	}

}
