package pt.tecnico.mydrive.domain;

import java.math.BigInteger;
import java.util.Random;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.LoginTimeExpiredException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

public class Login extends Login_Base {
    
    public Login(User user, String pw) {
    	super();
    	deleteInvalidLogins(user);
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
    
    
    public void checkTimeout() throws LoginTimeExpiredException{ //Verifies if the login 
    	if((getDateCreated().getMillis() - new DateTime().getMillis()) > 720000){
    		setDirectory(null);
    		setUser(null);
    		throw new LoginTimeExpiredException();
    	}
    	//we must renew DateTime after each checkTimeout()!!
    }
    
    public String read(String filename)throws EntityDoesNotExistException, WrongFileTypeException{
    	Entity file = getDirectory().getByName(filename);
    	if(file == null)
    		throw new EntityDoesNotExistException(filename);
    	if(file.checkType().equals("dir"))
    		throw new WrongFileTypeException();
    	return ((PlainFile) file).read(this);
    }
    
    public void deleteInvalidLogins(User user){ //Checks if user logins are still valid
    	for(Login login : user.getLoginSet()){
    		login.checkTimeout();
    	}
    }
    
}
