package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;


public class DeleteFileService extends FileSystemService {
	Login login;
	long token;
	String filename;
	boolean hasDeletePermissions;
	
	
	public DeleteFileService(String filename) throws UnknownTokenException {
		this.login = getLogin(token);
		this.token = this.login.getToken();
		this.filename = filename;
		
		checkPermissions(login.getUser().getMask());
		
		
	}
	
	public void checkPermissions(byte[] permissions) throws UserHasInvalidPermissionsException {
		this.hasDeletePermissions = false;
		if(permissions[3] == 1) {
			this.hasDeletePermissions = true;
		}
	}
	
	@Override
	protected void dispatch() {
	//		if(hasDeletePermissions) {
//			try {
//				login.getUser().getEntityByName(filename).delete();	
//			} catch(EntityDoesNotExistException e) {
//				throw e;
//			}
//		}
	}

}
