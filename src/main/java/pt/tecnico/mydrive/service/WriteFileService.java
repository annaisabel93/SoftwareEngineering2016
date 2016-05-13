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
    	this.fileName = fileName;
        this.content = content;
    }



	@Override
    public final void dispatch() throws TexFileDoesNotExistException, UserHasInvalidPermissionsException{
		this.login = getLogin(token);
        login.getDirectory().getByName(fileName).checkPermissions(this.login.getUser(), "write");
        Login login = getLogin(token);
        PlainFile text = (PlainFile) this.login.getDirectory().getByName(this.fileName);
		if(text == null ){
        	throw new TexFileDoesNotExistException(this.fileName);
        }
        text.addContent(this.login.getUser(), content);
    }
}
