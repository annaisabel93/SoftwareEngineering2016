package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertNull;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;
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
	
// ver se a data foi alterada, se conteudo foi alterado 
	
	@Test
	public void success() {
		FileSystem fs = FileSystem.getInstance();
		
		WriteFileService writetext = new WriteFileService(this.token, "testFile", "content");
		writetext.execute();
		
		//checks
		
		Entity fil = fs.getUserByUsername("filipac").getHome().getByName("file");
        assertNull("File content ", fil);
        
		//PlainFile file = service.result();
		//assertEquals(fil.getContent(), "testContent");
	}
	
	//file does not exist
	@Test(expected = TexFileDoesNotExistException.class)
	public void invalidWriteFileWithNonexistingFilename() {
		WriteFileService service = new WriteFileService(this.token, "File", "content");
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
	
//	@Test(expected = UserHasInvalidPermissionsException.class)
//	public void invalidWriteFileWithNonexistingFilename2() {
//		WriteFileService service = new WriteFileService(this.token, "noFile", "content");
//		service.execute();
//	} 
}
