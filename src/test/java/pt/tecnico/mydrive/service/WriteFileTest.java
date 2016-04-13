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
//import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class WriteFileTest extends AbstractServiceTest{

	private long token;

	protected void populate() {
		
		FileSystem fs = FileSystem.getInstance();
	
		byte[] mask = {1, 0, 1, 1};
		byte[] mask2 = {1, 1, 1, 1};
		String username = "filipac";
		String username2 = "filipaco";
		String name = username;
		String password = username;
		String homeDir = "/home/" + username;
		User user = new User(fs, name, username, password, mask, homeDir);
		LoginService service = new LoginService(username, password);
		service.execute();
		
		this.token =  service.getToken();
		Login login = user.getLoginbyToken(service.getToken());
		
		String filename = "testFile";
		long id = 1;
		DateTime lastModified = new DateTime();
		String content = "content";
		PlainFile file = new PlainFile(fs.getUserDir(username), filename, user, id, lastModified, content);
		
	}
	
//ficheiro nao existe, user nao tem permissoes para escrever no ficheiro, ver se a data foi alterada, se conteudo foi alterado 
	
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
	
	@Test(expected = TexFileDoesNotExistException.class)
	public void invalidWriteFileWithNonexistingFilename() {
		WriteFileService service = new WriteFileService(this.token, "File", "content");
		service.execute();
	}
	
//	@Test(expected = UserHasInvalidPermissionsException.class)
//	public void userHasNoPermissionsToEditFile() {
//		WriteFileService service = new WriteFileService(this.token, "testFile", "content");
//		service.execute();
//	}
	
//	@Test(expected = UserHasInvalidPermissionsException.class)
//	public void invalidWriteFileWithNonexistingFilename2() {
//		WriteFileService service = new WriteFileService(this.token, "noFile", "content");
//		service.execute();
//	}
	//Se utilizador nao tiver permissoes para escrever no ficheiro dado (excepcao--> check)
}
