package pt.tecnico.mydrive.domain;

import java.math.BigInteger;
import java.util.Random;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.LoginTimeExpiredException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

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
    
    
    public void checkTimeout() throws LoginTimeExpiredException{ 
    	if((getDateCreated().getMillis() - new DateTime().getMillis()) > 720000){
    		setDirectory(null);
    		setUser(null);
    		throw new LoginTimeExpiredException();
    	}
    }
    
    
    public boolean checkExistance(String absolutePath) throws ContentCannotBeNullException{ //ve se o ficheiro em questao existe
    	Directory root = getUser().getSystem().getRootDir(); //Starts on rootDir
    	if(absolutePath == null){
    		throw new ContentCannotBeNullException("nulo");
    	}
    	String[] items= absolutePath.split("/"); //splits the full path into Directories
    	
    	for(int x = 1; x < items.length; x++){ // Goes to each array position to move 1 by 1 until the last position (destiny)
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
    			return false;
    		}
    		root = dir;
    	}
    	return true;
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
