package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
//exceptions
//

public class ReadFileTest extends AbstractServiceTest {
	
		
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
	
			
		String name = "Someone";
		String userName = "SS";
		String password = "***";
		byte[] mask = {0,0,0,0};
		String homeDir = "/home/SS";
	
		User u = new User(fs, name, userName, password, mask , homeDir);

		LoginService service = new LoginService(userName, password);
		Login log = u.getLoginbyToken(service.getToken());
//		new PlainFile();
//		new App();
//		new Link();
	}
/*
	@Test
	public void success() {
		FileSystem fs = FileSystem.getInstance();

		//ReadFileService service = new ReadFileService();
		//receiveing a user and reading all the files that belong to user?
		//service.execute();
		//List<Entity> fs = service.result();
		//
		//check files parameters
		//assertEquals("Directory parent should be "/" ", "/", fs.get(0).getParent().getFileName());
		//assertEquals("Directory name should be Some Directory", "Some Directory", fs.get(0).getFileName());
		//assertEquals("User name whom Directory belongs to should be Someone", "Someone",fs.get(0).getName());
	}
*/
}









