package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;

public class LoginTest extends AbstractServiceTest {

	@Override
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate!", "1234", new byte[] {1,1,1,1} , "/home/chocolate");
		Login login = new Login(usr, "1234");
		
		
	}

//	@Test
//	public void success() {
//		final String username = "Ana";
//		LoginService service = new LoginService("Ana", "1234");
//		service.execute();
//
//		//FIXME check user is logged
//		//assertTrue("User was not logged", FileSystemService.getLogin(token));
//	}
	

}
