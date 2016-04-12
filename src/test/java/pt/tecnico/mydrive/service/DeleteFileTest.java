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


public class DeleteFileTest extends AbstractServiceTest {
	
	
	private long token;
	
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate!", "1234", new byte[] {1,1,1,1} , "/home/chocolate");
		LoginService service = new LoginService("chocolate!", "1234");
		service.execute();
		this.token =  service.getToken();
		Login login = usr.getLoginbyToken(service.getToken());
		Directory dir = new Directory(login.getDirectory(), "dir", usr, 20000, date);
		PlainFile file = new PlainFile(login.getDirectory(), "text", login.getUser(), 1, date, "ola ana");
		App app = new App(login.getDirectory(), "app", login.getUser(), 2, date, "adeus ana");
		Link link = new Link(login.getDirectory(), "link", login.getUser(), 3, date, "ok");
		

		
	}
	@Test
	public void sucess(){
		FileSystem fs =FileSystem.getInstance();
	

		DeleteFileService removeText = new DeleteFileService(this.token, "text");
		removeText.execute();
	
		DeleteFileService removeApp = new DeleteFileService(this.token , "app");
		removeApp.execute();

		DeleteFileService removeLink = new DeleteFileService(this.token, "link");
		removeLink.execute();

		DeleteFileService removeDir = new DeleteFileService(this.token, "dir");
		removeDir.execute();				
		
		
		// check if all files were removed
      
		Entity dir = fs.getUserByUsername("chocolate!").getHome().getByName("dir");
        assertNull("Directory was not removed", dir);
        
        Entity f = fs.getUserByUsername("chocolate!").getHome().getByName("text");
        assertNull("Text file was not removed", f);
        
        Entity a = fs.getUserByUsername("chocolate!").getHome().getByName("app");
        assertNull("App was not removed", a);
        
        Entity l = fs.getUserByUsername("chocolate!").getHome().getByName("link");
        assertNull("Link was not removed", l);
	
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
	
        
		
}
