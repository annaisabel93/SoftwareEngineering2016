package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.domain.FileSystem;

public class ChangeDirectoryService extends FileSystemService {

    private Login login;
    private String directoryName;
    private Directory workingDir;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ChangeDirectoryService(FileSystem fs1, long token, String directoryName1) throws UnknownTokenException{
    	this.login = getLogin(token);
        this.directoryName = directoryName1;
        this.workingDir = this.login.getDirectory();
    }

    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
	if (this.workingDir.getByName(this.directoryName) instanceof Directory){
		this.login.setDirectory((Directory)this.workingDir.getByName(this.directoryName));
	}
	else
	    throw new DirectoryDoesNotExistWithinDirectoryException(this.directoryName);
    }
}