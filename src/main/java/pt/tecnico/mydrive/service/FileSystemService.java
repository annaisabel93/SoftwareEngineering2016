package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Entity;
//import pt.tecnico.phonebook.exception.PersonDoesNotExistException;
//import pt.tecnico.phonebook.exception.PhoneBookException;

public abstract class FileSystemService {
    protected static final Logger log = LogManager.getRootLogger();

    @Atomic
    public final void execute() /*throws FileSystemException*/ {
        dispatch();
    }

    static FileSystem getFileSystem() {
        return FileSystem.getInstance();
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
