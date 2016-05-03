package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UnknownTokenException;

public class ChangeDirectoryTest extends AbstractServiceTest {
	
	private long token;
	private long token2;
	private Login login;
	private Login login2;
	private String dirtogo;
	private Directory dir;
	
	protected void populate() {
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate1", "12345678", new byte[] {1,1,1,0} , "/home/chocolate1");
		User usr2 = new User(fs, "AnaG", "chocolate2", "12345678", new byte[] {1,1,1,0} , "/home/chocolate2");
		LoginService service1 = new LoginService("chocolate1", "12345678");
		service1.execute();
		
		LoginService service2 = new LoginService("chocolate2", "12345678");
		service2.execute();
		
		this.token =  service1.getToken();
		this.token2 =  service2.getToken();
		
		this.login = service1.getLogin(this.token);
		this.login2 = service2.getLogin(this.token2);
		
		this.dirtogo = "/home/chocolate2";
		
		Directory dir = new Directory(login.getDirectory(), "dir", login.getUser(), 20000, date);
		this.dir = dir;
		
	}
	@Test
	public void success(){
		
		FileSystem fs =FileSystem.getInstance();
		ChangeDirectoryService service = new ChangeDirectoryService(this.token, "/home/chocolate2");
		service.execute();
		System.out.println("current dir: " + this.login.getDirectory().getFilename());
		assertEquals(dirtogo, this.login.getDirectory().getPath("/chocolate2"));

	}
	
	@Test(expected = UnknownTokenException.class)
	public void wrongToken(){
		long invalidToken = 0L;
		 ChangeDirectoryService service = new ChangeDirectoryService(invalidToken, dirtogo);
		 service.execute();
	}
	
	@Test(expected = EntityDoesNotExistException.class)
	public void wrongDir(){
		 ChangeDirectoryService service = new ChangeDirectoryService(this.token, dir.toString());
		 service.execute();
	}
	
//	@Test(expected = UserHasInvalidPermissionsException.class)
//	public void userHasNoPermissionsToDeleteFile() {
//		ChangeDirectoryService change = new ChangeDirectoryService(this.token2, "/home/chocolate2");
//		change.execute();
//		WriteFileService service = new WriteFileService(this.token2, "text", "content");
//		service.execute();
//	}
	
}
