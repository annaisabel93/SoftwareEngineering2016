package pt.tecnico.mydrive.service;


import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongPasswordException;
import pt.tecnico.mydrive.exception.TokenAlreadyInUseException;
import java.math.BigInteger;
import java.util.Random;

public class LoginService extends FileSystemService {

    private String username;
    private String password;
    private FileSystem fs;
    long token;
    
    public LoginService(FileSystem fs1, String username1, String password1){
        this.username = username1;
        this.password = password1;
        this.fs = fs1;
        this.token = new BigInteger(64, new Random()).longValue();
    }

    @Override
    
    public final void dispatch() throws UsernameDoesntExistException, WrongPasswordException, TokenAlreadyInUseException {
    	
    		for(User user1 : fs.getUserSet()){ //Verifies if the given token is already in use by another login
    			for(Login login : user1.getLoginSet()){
    				if(login.getToken() == this.token){
    					throw new TokenAlreadyInUseException(this.token);
    				}
    			}
    		}
    		User user = this.fs.getUserByUsername(username);  // Verifies if User already exists (throws exception if it doesnt )
    		if ((user.getPassword().equals(this.password)) == false){// Verifies if password is correct
    			throw new WrongPasswordException();
    		}
    		new Login(user, this.token);
    }
    
    
    public long getToken(){
    	return this.token;
    }
}