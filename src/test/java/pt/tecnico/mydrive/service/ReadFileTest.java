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
//exceptions
//

public class ReadFileTest extends AbstractServiceTest {
	
		
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
		
		byte[] mask = {0,0,0,0};
		DateTime date = new DateTime();
		
		User u = new User(fs, "Someone", "SO", "****", mask , "homie");
		Directory d = new Directory(fs.getRootDir(), "Some Directory", u, 5 , date); 		
		new Directory(d, "D", u, 6, date);
		new PlainFile(d, "D", u, 7, date, "ZA");
		new App(d, "S", u, 8, date, "WS");
		new Link(d, "A", u, 9, date, "SA");	
	}

	@Test
	public void success() {
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

}









