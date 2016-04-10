package pt.tecnico.mydrive.domain;

import org.joda.time.DateTime;
import pt.tecnico.mydrive.exception.LoginTimeExpiredException;

public class Login extends Login_Base {
    
    public Login(User user, long token1) {
    	super();
    	setDateCreated(new DateTime());
    	setToken(token1);
    	setUser(user);
    	setDirectory(user.getHome());
    	
    }
    
    
    public void loginValidation() throws LoginTimeExpiredException{
    	
    }
    
}
