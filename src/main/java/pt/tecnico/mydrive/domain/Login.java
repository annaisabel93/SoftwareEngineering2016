package pt.tecnico.mydrive.domain;

import java.math.BigInteger;
import java.util.Random;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.LoginTimeExpiredException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.VariableDoesNotExistException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;
import pt.tecnico.mydrive.exception.WrongPasswordException;
import pt.tecnico.mydrive.exception.InexistentPointerForLinkException;

public class Login extends Login_Base {
    
    public Login(User user, String pw) throws WrongPasswordException, UsernameDoesntExistException{
    	super();
    	
    	if (user == null) {
			throw new UsernameDoesntExistException("Username does not exist");
		}
    	
    	FileSystem.deleteInvalidLogins(user);
    	//FIXME
    	setDateCreated(new DateTime());
		long token = new BigInteger(64, new Random()).longValue();
    	while(user.getSystem().validateToken(token) == false){
    		token = new BigInteger(64, new Random()).longValue();
    	}
    	if ((user.getPassword().equals(pw)) == false){// verifica pw
			throw new WrongPasswordException();
		}
    	setToken(token);
    	setUser(user);
    	setDirectory(user.getHome());
    	
    }
    
    
    public void moveAbsolute(String absolutePath) throws DirectoryDoesNotExistWithinDirectoryException{ //Goes from rootDir until the desired Directory
    	Directory root = getUser().getSystem().getRootDir(); //Starts on rootDir
    	String[] items= absolutePath.split("/"); //splits the full path into Directories
    	for(int x = 1; x < items.length; x++){ // Goes to each array position to move 1 by 1 until the last position (destiny)
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
    			throw new DirectoryDoesNotExistWithinDirectoryException(items[x]);
    		}
    		root = dir;
    	}
    	setDirectory(root);
    }
    
    public void moveRelative(String relativePath){// igual, mas parte do current directory
    	Directory root = getDirectory(); 
    	String[] items = relativePath.split("/"); 
    	for(int x = 0; x < items.length; x++){
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
    			throw new DirectoryDoesNotExistWithinDirectoryException(items[x]);
    		}
    		root = dir;
    	}
    	setDirectory(root);
    	System.out.println(root.getPath(""));
    }
    
    
    public void checkTimeout() throws LoginTimeExpiredException{ 
    	if((getDateCreated().getMillis() - new DateTime().getMillis()) > 720000){
    		setDirectory(null);
    		setUser(null);
    		throw new LoginTimeExpiredException();
    	}
    	if ((getDateCreated().getMillis() - new DateTime().getMillis()) > 600000){
    		if (getUser().getUserName().equals("root")){
        		setDirectory(null);
        		setUser(null);
        		throw new LoginTimeExpiredException();
    		}
    		
    	}
    	
    }
    
    
    public boolean checkExistance(String absolutePath) throws ContentCannotBeNullException, InexistentPointerForLinkException { //ve se o ficheiro em questao existe
    	Directory root = getUser().getSystem().getRootDir(); //Starts on rootDir
    	if(absolutePath == null){
    		throw new ContentCannotBeNullException("nulo");
    	}
    	String[] items= absolutePath.split("/"); //splits the full path into Directories
    	
    	for(int x = 1; x < items.length; x++){ // Goes to each array position to move 1 by 1 until the last position (destiny)
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
			//receives dirname in constructor
			//in this case it is inexistent, so it will be null
    			throw new InexistentPointerForLinkException(null);
    		}
    		root = dir;
    	}
    	return true;
    }
    
    public Variable getVariableByName(String variableName) throws VariableDoesNotExistException{
    	for(Variable variable : getVariableSet()){
    		if(variable.getName().equals(variableName))
    			return variable;
    	}
    	throw new VariableDoesNotExistException();
    }
    /*
    public String read(String filename)throws EntityDoesNotExistException, WrongFileTypeException, UserHasInvalidPermissionsException{
    	Entity file = getDirectory().getByName(filename);
    	if(file == null)
    		throw new EntityDoesNotExistException(filename);
    	if(file.checkType().equals("dir"))
    		throw new WrongFileTypeException();
    	if(file.checkPermissions(this, "read") == false)
    		throw new UserHasInvalidPermissionsException();
    	return ((PlainFile) file).read(this);
    }*/
}
