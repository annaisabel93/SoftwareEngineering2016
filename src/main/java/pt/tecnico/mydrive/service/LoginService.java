package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistInsideWorkingDirException;;

public class LoginService extends FileSystemService {

    private String username;
    private String password;
    
    public LoginService(String username1, String password1) {
        this.username = username1;
        this.password = password1;
    }

    @Override
    
    public final void dispatch() throws DirectoryDoesNotExistInsideWorkingDirException {
	//if (this.workingDir.getByName(this.directoryName) instanceof Directory){
	//	this.login.setDirectory((Directory)this.workingDir.getByName(this.directoryName));
	//}
	//else
	 //   throw new DirectoryDoesNotExistInsideWorkingDirException(this.directoryName);
    }
}