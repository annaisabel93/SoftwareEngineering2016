package pt.tecnico.mydrive.service;

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
import pt.tecnico.mydrive.exception.AppDoesNotExistException;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.LinkDoesNotExistException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;


public class DeleteFileTest extends AbstractServiceTest {
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate!", "1234", new byte[] {0,0,0,0} , "/home/chocolate");
		LoginService service = new LoginService("chocolate!", "1234");
		Login login = usr.getLoginbyToken(service.getToken());
		Directory dir = new Directory(login.getDirectory(), "strawberry", usr, 20000, date);
		PlainFile file = new PlainFile(login.getDirectory(), "text", login.getUser(), 1, date, "ola ana");
		App app = new App(login.getDirectory(), "app", login.getUser(), 2, date, "adeus ana");
		Link link = new Link(login.getDirectory(), "link", login.getUser(), 3, date, "ok");

		
		
	}
	


        
//        assertEquals("Invalid number of files", 0, FileSystem.service.getUserbyUserName.getFile().size())
		
	}
	
//	@Test(expected = DirectoryDoesNotExistWithinDirectoryException.class)
//	
//	public void removeDirectory(){
//		final String dirname = "strawberry";
//		 DeleteFileService service = new DeleteFileService(dirname);
//		 service.execute();
//	}
//	
//	@Test(expected = TexFileDoesNotExistException.class)
//	public void removePlainFile(){
//		final String file = "text";
//		 DeleteFileService service = new DeleteFileService(text);
//		 service.execute();
//	}
//	
//	@Test(expected = AppDoesNotExistException.class)
//	public void removeApp(){
//		final String app = "app";
//		 DeleteFileService service = new DeleteFileService(app);
//		 service.execute();
//	}
//	
//	@Test(expected = LinkDoesNotExistException.class)
//	public void removeLink(){
//		final String link = "link";
//		 DeleteFileService service = new DeleteFileService(link);
//		 service.execute();
//	}
        
		
//}
