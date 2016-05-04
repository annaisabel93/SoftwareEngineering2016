package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

public class LoginTest extends AbstractServiceTest {

	private User user = null;
	
	@Override
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
		this.user = new User(fs, "Ana", "chocolate!", "12345678", new byte[] {1,1,1,1} , "/home/chocolate");
		Login login = new Login(this.user, "12345678");
	}

	@Test
	public void success() {

		LoginService login = new LoginService("chocolate!", "12345678");
		login.execute();
		

		// user is right
		
		String usr = user.getUserName();
        assertEquals("chocolate!", usr);
		
		String password = user.getPassword();
        assertEquals("12345678", password);
	}

	@Test(expected = UsernameDoesntExistException.class)
	public void wrongusername(){
		LoginService service = new LoginService("ana", "12345678");
		service.execute();
	}
	
	@Test(expected = WrongPasswordException.class)
	public void wrongpassword(){
		LoginService service = new LoginService("chocolate!", "87654321");
		service.execute();
	}

}
