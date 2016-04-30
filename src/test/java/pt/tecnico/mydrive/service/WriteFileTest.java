package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import junit.framework.TestFailure;
import pt.ist.fenixframework.dml.runtime.DomainBasedMap.Getter;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class WriteFileTest extends AbstractServiceTest{

	private long token;
	private long token2;
	private Login login;
	private Login login2;

	protected void populate() {
		
		FileSystem fs = FileSystem.getInstance();
	
		byte[] mask1 = {1, 0, 1, 1};
		String username1 = "filipac";
		String username2 = "filipaco";
		String name = "ola";
		String password = "password";
		String homeDir1 = "/home/" + username1;
		String homeDir2 = "/home/" + username2;
		User userWithPermissions = new User(fs, name, username1, password, mask1, homeDir1);
		User userWithoutPermissions = new User(fs, name, username2, password, mask1, homeDir2);
		LoginService service1 = new LoginService(username1, password);
		
		LoginService service2 = new LoginService(username2, password);
		service1.execute();
		service2.execute();
		
		this.token =  service1.getToken();
		this.token2 =  service2.getToken();
		this.login = service1.getLogin(this.token);
		this.login2 = service2.getLogin(this.token2);
		String filename = "testFile";
		long id = 1;
		DateTime lastModified = new DateTime();
		String content = "content";
		new PlainFile(login.getDirectory(), filename, userWithPermissions, id, lastModified, content);
		
	}
	
	
	@Test
	public void success() {
		FileSystem fs = FileSystem.getInstance();
		
		WriteFileService writetext = new WriteFileService(this.token, "testFile", "content");
		writetext.execute();
		
		//checks
		
		Entity fil = login.getDirectory().getByName("testFile");
		PlainFile f = (PlainFile) fil;
        assertEquals("content", f.getContent());
        
	}
	
	//file does not exist
	@Test(expected = EntityDoesNotExistException.class)
	public void invalidWriteFileWithNonexistingFilename() {
		WriteFileService service = new WriteFileService(this.token, "Filea", "content");
		service.execute();
	}
	
	 
	//user has invalid permissions to write on file
	@Test(expected = UserHasInvalidPermissionsException.class)
	public void userHasNoPermissionsToEditFile() {
		//login2.setDirectory(login.getDirectory());
		ChangeDirectoryService change = new ChangeDirectoryService(this.token2, "/home/filipac");
		change.execute();
		WriteFileService service = new WriteFileService(this.token2, "testFile", "content");
		service.execute();
	}
	
	//checks if the file modification date is altered when a file is written
	@Test
	public void checksModifiedDate() {
		Entity e = login.getDirectory().getByName("testFile");
		DateTime dateBefore = e.getLastModified();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WriteFileService service = new WriteFileService(this.token, "testFile", "content");
		service.execute();
		e = login.getDirectory().getByName("testFile");
		DateTime dateAfter = e.getLastModified();
		//assertFalse(dateBefore.equals(dateAfter));
		assertNotEquals(dateBefore, dateAfter);
	}
	
	
	//checks if the content of the file is altered 
	@Test
	public void unchangedContentAfterWrite() {
		Entity e = login.getDirectory().getByName("testFile");
		PlainFile f = (PlainFile) e;
		String contentBefore = f.getContent();
		WriteFileService service = new WriteFileService(this.token, "testFile", "different");
		service.execute();
		Entity e2 = login.getDirectory().getByName("testFile");
		PlainFile f2 = (PlainFile) e2;
		String contentAfter = f2.getContent();
		assertNotEquals(contentBefore, contentAfter);
	}
	
	//checks if the token is invalid 
	@Test(expected = UnknownTokenException.class)
	public void invalidToken() {
		FileSystem fs = FileSystem.getInstance();
		long invalidToken = 0L;
		WriteFileService writetext = new WriteFileService( invalidToken, "testFile", "content");
		writetext.execute();
	}

}
