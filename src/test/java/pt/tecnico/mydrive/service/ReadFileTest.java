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

 
import pt.tecnico.mydrive.exception.WrongFileTypeException;

public class ReadFileTest extends AbstractServiceTest {

	private long uToken;
//	private FileSystem fs;	
	
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
	
			
		String name = "Someone";
		String userName = "SS";
		String password = "***";
		byte[] mask = {1,0,0,0};
		String homeDir = "/home/SS";
	
		User u = new User(fs, name, userName, password, mask , homeDir);
		//fs.addUser(u);

			
		LoginService service = new LoginService(userName, password);
		service.execute();
		this.uToken = service.getToken();
		Login log = u.getLoginbyToken(service.getToken());
		// /home/SS


			
		PlainFile p1 = new PlainFile(log.getDirectory(), this.fileNames[0], u, fs.Counter(), new DateTime(), this.contents[0] );
		PlainFile p2 = new PlainFile(log.getDirectory(), this.fileNames[1], u, fs.Counter(), new DateTime(),this.contents[1]);
		Link l1 = new Link(log.getDirectory(), this.fileNames[2], u, fs.Counter(), new DateTime(), this.contents[2]);
		Link l2 = new Link(log.getDirectory(), this.fileNames[3], u, fs.Counter(), new DateTime(), this.contents[3]);
		App a1 = new App(log.getDirectory(), this.fileNames[4], u, fs.Counter(), new DateTime(), this.contents[4]);
		App a2 = new App(log.getDirectory(), this.fileNames[5], u, fs.Counter(), new DateTime(), this.contents[5]);
		
		/*i.addFile(p1);
		d.addFile(p2);
		d.addFile(l1);
		d.addFile(l2);
		d.addFile(a1);
		d.addFile(a2);*/
		
			
	}

	public void initializeReadFileServices( ReadFileService[] rfsArray) {
		int i;
		for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i] = new ReadFileService(this.uToken,this.fileNames[i]);
		} 		
	}

	public void verifyContentPlains(ReadFileService[] rfsArray ) throws WrongFileTypeException {	
		int i;
		for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i].execute();
			String content = rfsArray[i].returnContent(this.fileNames[i]);	
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


		

}









