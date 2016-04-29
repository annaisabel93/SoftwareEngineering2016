package pt.tecnico.mydrive.service;


import pt.tecnico.mydrive.domain.Directory;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.UnknownTokenException;


public class ListDirectoryService extends FileSystemService {

    private Login login;
    private String[] result;
   
    private Directory workingDir;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ListDirectoryService(long token) throws UnknownTokenException{
    	this.login = getLogin(token);
     
        this.workingDir = this.login.getDirectory();
    }

    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
    	this.result = this.workingDir.list();
    	
    	
    }
    
    public String[] getResult(){
    	return this.result;
    }
    
    
}