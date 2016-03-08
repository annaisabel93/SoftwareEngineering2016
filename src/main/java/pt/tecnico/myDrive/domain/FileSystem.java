package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.FenixFramework;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
	
    public static FileSystem getInstance(){
    	FileSystem fs = FenixFramework.getDomainRoot().getFilesystem();
    	if(fs != null)
    		return fs;
    	else{
    		log.trace("new FileSystem");
    		return new FileSystem(0);
    	}
    }
    
    private FileSystem(int counter) {
        setRoot(FenixFramework.getDomainRoot());
        setCounter(counter);
    }
    
}
