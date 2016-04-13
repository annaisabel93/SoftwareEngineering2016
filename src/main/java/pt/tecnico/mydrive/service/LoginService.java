package pt.tecnico.mydrive.service;


import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.TokenAlreadyInUseException;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

public class LoginService extends FileSystemService {

    private String username;
    private String password;
    private FileSystem fs;
    long token;
    
    public LoginService(String username1, String password1){
        this.username = username1;
        this.password = password1;
        this.fs = getFileSystem();
    }

    @Override
    
    public final void dispatch() throws UsernameDoesntExistException, WrongPasswordException, TokenAlreadyInUseException {    		
    		User user = this.fs.getUserByUsername(username);  // Verifies if User already exists (throws exception if it doesnt )
    		Login login= new Login(user, this.password); //passar pw e o token tem que ser gerado la
    		this.token = login.getToken();
    }
    
    
    public long getToken(){
    	return this.token;
    }
}