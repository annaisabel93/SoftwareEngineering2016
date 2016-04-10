package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UnknownTokenException;

public abstract class FileSystemService {
    protected static final Logger log = LogManager.getRootLogger();

    @Atomic
    public final void execute() /*throws FileSystemException*/ {
        dispatch();
    }

    static FileSystem getFileSystem() {
        return FileSystem.getInstance();
    }
    
    
    static Login getLogin(long token) throws UnknownTokenException{
    	FileSystem fs1 = getFileSystem();
    	for(User user : fs1.getUserSet()){
    		for(Login login : user.getLoginSet()){
    			if(login.getToken() == token){
    				return login;
    			}
    		}
    	}
    		throw new UnknownTokenException(token);
    }

    /*static Entity getEntity(String fileName) throws PersonDoesNotExistException {        
	Entity e = getFileSystem().getEntityByName(fileName);
        if (e == null)
            	return;
		//throw new PersonDoesNotExistException(personName);
        return e;
    }*/

    protected abstract void dispatch() /*throws PhoneBookException*/;
}
