package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;


public class DeleteFileTest extends AbstractServiceTest {
	
	private long token;
	private long token2;
	private Login login;
	private Login login2;
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate1", "1234", new byte[] {1,1,1,0} , "/home/chocolate1");
		User usr2 = new User(fs, "AnaG", "chocolate2", "1234", new byte[] {1,1,1,0} , "/home/chocolate2");
		LoginService service1 = new LoginService("chocolate1", "1234");
		service1.execute();
		LoginService service2 = new LoginService("chocolate2", "1234");
		service2.execute();
		this.token =  service1.getToken();
		this.token2 =  service2.getToken();
		this.login = service1.getLogin(this.token);
		this.login2 = service2.getLogin(this.token2);
		Directory dir = new Directory(login.getDirectory(), "dir", login.getUser(), 20000, date);
		PlainFile file = new PlainFile(login.getDirectory(), "text", login.getUser(), 1, date, "ola ana");
		App app = new App(login.getDirectory(), "app", login.getUser(), 2, date, "adeus ana");
		Link link = new Link(login.getDirectory(), "link", login.getUser(), 3, date, "ok");
	}
	
	@Test
	public void success(){
		
		FileSystem fs =FileSystem.getInstance();
	
		DeleteFileService removeText = new DeleteFileService(this.token, "text");
		removeText.execute();
	
		DeleteFileService removeApp = new DeleteFileService(this.token , "app");
		removeApp.execute();

		DeleteFileService removeLink = new DeleteFileService(this.token, "link");
		removeLink.execute();
		System.out.println("here");
		DeleteFileService removeDir = new DeleteFileService(this.token, "dir");
		removeDir.execute();				
			
		// check if all files were removed   
        assertEquals("Invalid number of files", 0, FileSystemService.getLogin(token).getDirectory().getFileSet().size());            
	}		
	
	@Test(expected = EntityDoesNotExistException.class)
	public void removePlainFile(){
		 DeleteFileService service = new DeleteFileService(this.token, "text2");
		 service.execute();
	}
	
	@Test(expected = EntityDoesNotExistException.class)
	public void removeApp(){
		 DeleteFileService service = new DeleteFileService(this.token, "app2");
		 service.execute();
	}
	
	@Test(expected = EntityDoesNotExistException.class)
	public void removeLink(){
		 DeleteFileService service = new DeleteFileService(this.token, "link2");
		 service.execute();
	}
	
	@Test(expected = EntityDoesNotExistException.class)
	public void removeDirectory(){
		 DeleteFileService service = new DeleteFileService(this.token, "dirname2");
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
