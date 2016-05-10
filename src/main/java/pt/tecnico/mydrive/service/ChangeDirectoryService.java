package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.domain.FileSystem;

public class ChangeDirectoryService extends FileSystemService {

    private Login login;
    private String directoryPath;
    private Directory workingDir;
    private String result;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ChangeDirectoryService(long token, String dirpath) throws UnknownTokenException{
    	this.login = getLogin(token);
        this.directoryPath = dirpath;
        this.workingDir = this.login.getDirectory();
    }

    
    public String getResult(){
    	return this.result;
    }
    
    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
    	if(directoryPath.equals(".")){ //Print current directory
    		System.out.println(this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename()));
    		return;
    	}
    	if(directoryPath.equals("..")) { //Goes to parent directory
    		if(this.workingDir.getParent() == this.workingDir){
    			System.out.println(this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename()));
    			return;
    		}
    		login.setDirectory(this.workingDir.getParent());
    		System.out.println(this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename()));
    		return;
    	}
    	String[] items= this.directoryPath.split("/");
    	if(items[0].equals("")){
    		this.login.moveAbsolute(this.directoryPath);
    		System.out.println(this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename()));
    		return;
    	}
    	else{
    		this.login.moveRelative(this.directoryPath);
    		this.result = this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename());
    		System.out.println(this.login.getDirectory().getPath("/"+this.login.getDirectory().getFilename()));
    		return;
    	}
    	
    }
    
    
    
    
}