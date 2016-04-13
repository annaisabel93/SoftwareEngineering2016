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
   
    private Directory workingDir;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ListDirectoryService(long token) throws UnknownTokenException{
    	this.login = getLogin(token);
     
        this.workingDir = this.login.getDirectory();
    }

    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
    	User user = this.login.getUser();
    	
    	for(Entity entity: this.workingDir.getFileSet()) {
    		System.out.print(entity.checkType() + " ");
    		
    		byte[] array = user.getMask();
    		System.out.print(array[0]+""+ array[1]+""+array[2]+""+array[3] + " ");

    		if(entity.checkType() == "dir") {
    			System.out.print(((Directory) entity).getFileCount()+2 + " ");
    		}
    		else {
    			System.out.print(((PlainFile) entity).getContent().length() + " ");
    		}
    		System.out.print(entity.getOwner().getUserName() + " ");
    		System.out.print(entity.getId() + " ");
    		System.out.print(entity.getLastModified().getYear()+"-"+entity.getLastModified().getMonthOfYear()+"-"+entity.getLastModified().getDayOfMonth()+" "+entity.getLastModified().getHourOfDay()+":"+entity.getLastModified().getMinuteOfHour()+":"+entity.getLastModified().getSecondOfMinute() + " ");
    		System.out.println(entity.getFilename() + " ");
    		
    		
    	}
    }
    
}