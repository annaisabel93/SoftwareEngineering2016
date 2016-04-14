package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

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

import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;



public class ReadFileTest extends AbstractServiceTest {

	private long uToken1, uToken2;
	
	final String[] fileNames =    { "Sweet", 
					"WhiskeyInTheJar", 
					"Jane", 
					"Mini", 
					"MyDrive", 
					"Service"};
		
	final String[] contents = {	"Sweet Child O Mine", 
					null, 
					"/home/bananas",  
					"/home/peaches", 
					"package.pt.tecnico.mydrive.domain", 
					"package.pt.tecnico.mydrive.service" };

	
		
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
	
			
		String name1 = "Reaper", 
		       name2 = "Bunny" ;

		String userName1 = "GR", 
		       userName2 = "BB";

		String password = "***";

		byte[] mask1 = {1,0,0,0}, 
		       mask2 = {0,1,1,1};

		String homeDir1 = "/home/GR", 
		       homeDir2 = "/home/BB";
	
		User u1 = new User(fs, name1, userName1, password, mask1 , homeDir1);
		LoginService service1 = new LoginService(userName1, password);
		service1.execute();
		this.uToken1 = service1.getToken();
		Login log1 = u1.getLoginbyToken(service1.getToken());


		PlainFile p1 = new PlainFile(log1.getDirectory(), this.fileNames[0], u1, 1, new DateTime(), this.contents[0] );
		PlainFile p2 = new PlainFile(log1.getDirectory(), this.fileNames[1], u1, 2, new DateTime(),this.contents[1]);
		Link l1 = new Link(log1.getDirectory(), this.fileNames[2], u1, 3, new DateTime(), this.contents[2]);
		Link l2 = new Link(log1.getDirectory(), this.fileNames[3], u1, 4, new DateTime(), this.contents[3]);
		App a1 = new App(log1.getDirectory(), this.fileNames[4], u1, 5, new DateTime(), this.contents[4]);
		App a2 = new App(log1.getDirectory(), this.fileNames[5], u1, 6, new DateTime(), this.contents[5]);


		User u2 = new User(fs, name2, userName2, password, mask2 , homeDir2);
		LoginService service2 = new LoginService(userName2, password);
		service2.execute();
		this.uToken2 = service2.getToken();
		Login log2 = u2.getLoginbyToken(service2.getToken());


		Directory d1 = new Directory(log2.getDirectory(), "Xena", u2, 8, new DateTime());
		PlainFile p3 = new PlainFile(log2.getDirectory(), "Slack", u2, 10, new DateTime(), this.contents[0] );
		

			
	}

	public void initializeReadFileServices( ReadFileService[] rfsArray) {
		int i;
		for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i] = new ReadFileService(this.uToken1,this.fileNames[i]);
		} 		
	}

	public void verifyContentPlains(ReadFileService[] rfsArray ) throws WrongFileTypeException {	
		int i;
		for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i].execute();
			String content = rfsArray[i].getResult();	
			assertEquals("The content of plainfile" + this.fileNames[i] + "should be " + this.contents[i] , this.contents[i], content );
		}
	}

	

	@Test
	public void success(){

		ReadFileService[] rs = new ReadFileService[6];
		initializeReadFileServices(rs);
		try {
			this.verifyContentPlains(rs);	
		} catch (WrongFileTypeException e) {
			e.printStackTrace();
		}		

	}

/*	@Test(expected = UserHasInvalidPermissionsException.class)
	public void tryReadFile() {
		ReadFileService r = new ReadFileService(this.uToken2,"Slack");
		r.execute();
	}

	@Test(expected = TexFileDoesNotExistException.class)
	public void tryReadInexistentFile() {
		ReadFileService r = new ReadFileService(this.uToken2,"Zena");
		r.execute();
	}*/

/*	@Test(expected = CannotReadDirectory.class)
	public void tryReadDirectory() {
		ReadFileService r = new ReadFileService(this.uToken1,"Xena");
		r.execute();
	}

*/
	/*@Test
 * 	public void testLink() {
 * 	}		
*/
}









