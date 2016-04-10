package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

public class LoginService extends FileSystemService {

    private String username;
    private String password;
    private FileSystem fs;
    long token;
    
    public LoginService(FileSystem fs1, long token1, String username1, String password1) {
        this.username = username1;
        this.password = password1;
        this.fs = fs1;
        this.token = token1;
    }

    @Override
    
    public final void dispatch() throws UsernameDoesntExistException, WrongPasswordException {
    		User user = this.fs.getUserByUsername(username);
    		if ((user.getPassword().equals(this.password)) == false){
    			throw new WrongPasswordException();
    		}
    		new Login(user, this.token);
    }
}