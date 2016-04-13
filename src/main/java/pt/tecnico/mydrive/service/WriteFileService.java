package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class WriteFileService extends FileSystemService {
	Login login;
	private long token;
	private String fileName;
	private String content;
	
    public WriteFileService(long token, String fileName, String content) {
    	this.token = token;
    	this.login = getLogin(token);
        this.fileName = fileName;
        //checkPermissions(login.getUser().getMask());
        }

//    public void checkPermissions(byte[] permissions) throws UserHasInvalidPermissionsException {
//		if(permissions[1] == 0) {
//			throw new UserHasInvalidPermissionsException();
//		}
//	}
    
    @Override
    public final void dispatch() throws TexFileDoesNotExistException, UserHasInvalidPermissionsException{
    	
        Login login = getLogin(token);
        PlainFile text = (PlainFile) login.getDirectory().getByName(this.fileName);
		if(text == null ){
        	throw new TexFileDoesNotExistException(this.fileName);
        }
        text.setContent(content);
    }
}
