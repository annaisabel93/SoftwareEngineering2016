package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;

public class WriteFileService extends FileSystemService {
	
	private long token;
	private String fileName;
	private String content;
	
    public WriteFileService(long token, String fileName, String content) {
    	this.token = token;
        this.fileName = fileName;
        }

    @Override
    public final void dispatch() throws TexFileDoesNotExistException{
    	
        Login login = getLogin(token);
        PlainFile text = (PlainFile) login.getDirectory().getByName(this.fileName);
        text.setContent(content);
    	
    }
}
