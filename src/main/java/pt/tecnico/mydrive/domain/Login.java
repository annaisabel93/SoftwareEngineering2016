package pt.tecnico.mydrive.domain;

import org.joda.time.DateTime;
import pt.tecnico.mydrive.exception.LoginTimeExpiredException;

public class Login extends Login_Base {
    
    public Login(User user, long token1) {
    	super();
    	deleteInvalidLogins(user);
    	setDateCreated(new DateTime());
    	setToken(token1);
    	setUser(user);
    	setDirectory(user.getHome());
    }
    
    
    public void checkTimeout() throws LoginTimeExpiredException{ //Verifies if the login timeout has occured (2 hours)
    	if((getDateCreated().getMillis() - new DateTime().getMillis()) > 720000){
    		setDirectory(null);
    		setUser(null);
    		throw new LoginTimeExpiredException();
    	}
    	//we must renew DateTime after each checkTimeout()!!
    }
    
    
    public void deleteInvalidLogins(User user){ //Checks if user logins are still valid
    	for(Login login : user.getLoginSet()){
    		login.checkTimeout();
    	}
    }
    
}
