package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;



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
					"/home/GRA",  
					"/home/GRA", 
					"package.pt.tecnico.mydrive.domain", 
					"package.pt.tecnico.mydrive.service" };

	Login login1;
	Login login2;
		
	protected void populate() {
		FileSystem fs = FileSystem.getInstance();
	
			
		String name1 = "Reaper", 
		       name2 = "Bunny" ;

		String userName1 = "GRA", 
		       userName2 = "BBA";

		String password = "********";

		byte[] mask1 = {1,0,0,0}, 
		       mask2 = {0,1,1,1};

		String homeDir1 = "/home/GRA", 
		       homeDir2 = "/home/BBA";
	
		User u1 = new User(fs, name1, userName1, password, mask1 , homeDir1);
		LoginService service1 = new LoginService(userName1, password);
		service1.execute();
		this.uToken1 = service1.getToken();
		Login log1 = u1.getLoginbyToken(service1.getToken());
		this.login1 = log1;
		


		PlainFile p1 = new PlainFile(log1.getDirectory(), this.fileNames[0], u1, 1, new DateTime(), this.contents[0] );
		PlainFile p2 = new PlainFile(log1.getDirectory(), this.fileNames[1], u1, 2, new DateTime(),this.contents[1]);
		Link l1 = new Link(log1.getDirectory(), this.fileNames[2], u1, 3, new DateTime(), homeDir1);
		Link l2 = new Link(log1.getDirectory(), this.fileNames[3], u1, 4, new DateTime(), homeDir1);
		App a1 = new App(log1.getDirectory(), this.fileNames[4], u1, 5, new DateTime(), this.contents[4]);
		App a2 = new App(log1.getDirectory(), this.fileNames[5], u1, 6, new DateTime(), this.contents[5]);


		User u2 = new User(fs, name2, userName2, password, mask2 , homeDir2);
		LoginService service2 = new LoginService(userName2, password);
		service2.execute();
		this.uToken2 = service2.getToken();
		Login log2 = u2.getLoginbyToken(service2.getToken());
		this.login2 = log2;

		Directory d1 = new Directory(log2.getDirectory(), "Xena", u2, 8, new DateTime());
		PlainFile p3 = new PlainFile(log2.getDirectory(), "Slack", u2, 10, new DateTime(), this.contents[0] );
		
		//Testing links
//		Directory d2 = new Directory(log2.getDirectory(), "Caker", u2, 12, new DateTime());
//		PlainFile p4 = new PlainFile(log2.getDirectory(), "banana", u2, 11, new DateTime(), "hypes");		
//		Link l5 = new Link(log2.getDirectory(), "Sona", u2, 13, new DateTime(),"/" + d2.getFilename() + "/" + p4.getFilename());
//		Link l7 = new Link(log2.getDirectory(), "Sez", u2, 14, new DateTime(), "/" + d2.getFilename() + "/" + "meps");
		 
		
	}

	public void initializeReadFileServices( ReadFileService[] rfsArray) throws WrongFileTypeException {
		int i;
		//try {
			for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i] = new ReadFileService(this.uToken1,this.fileNames[i]);
				
			}/*
		} catch (WrongFileTypeException e) {
			e.printStackTrace();
		}*/		

	}

	public void verifyContentPlains(ReadFileService[] rfsArray ) {	
		int i;
		for ( i=0; i<rfsArray.length; i++) {
			rfsArray[i].execute();
			String content = rfsArray[i].getResult();	
			assertEquals("The content of plainfile" + this.fileNames[i] + "should be " + this.contents[i] , this.contents[i], content );
		}
	
	}

	

	@Test
	public void success() throws WrongFileTypeException{

		ReadFileService[] rs = new ReadFileService[6];
		try{
			initializeReadFileServices(rs);
			this.verifyContentPlains(rs);	
		} catch (WrongFileTypeException e) {
			e.printStackTrace();
		}		

	}

	@Test(expected = UserHasInvalidPermissionsException.class)
	public void tryReadFile() throws WrongFileTypeException{
		login1.setDirectory(login2.getDirectory());
		ReadFileService r = new ReadFileService(this.uToken1,"Slack");
		r.execute();
	}

	@Test(expected = EntityDoesNotExistException.class)
	public void tryReadInexistentFile() throws WrongFileTypeException {
		//try {
		
		ReadFileService r = new ReadFileService(this.uToken2,"Zena");
		r.execute();
		/*} catch (WrongFileTypeException e) {
			e.printStackTrace();
		}*/
	}

	@Test(expected = WrongFileTypeException.class)
	public void tryReadDirectory() {
		ReadFileService r = new ReadFileService(this.uToken2,"Xena");
		r.execute();
		
	}
	
	//checks if the token is invalid 
	@Test(expected = UnknownTokenException.class)
	public void invalidToken() {
		FileSystem fs = FileSystem.getInstance();
		long invalidToken = 0L;
		ReadFileService service = new ReadFileService(invalidToken, "Jane");
		service.execute();
	}

/*	@Test
	public void readLink() {
		ReadFileService service = new ReadFileService(this.uToken2, "Sona");
		service.execute();	
	}

	@Test
	public void readInexistentLink() {
		ReadFileService service = new ReadFileService(this.uToken2, "Sez");
		service.execute();
	}*/
}









