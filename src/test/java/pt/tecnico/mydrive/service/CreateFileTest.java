package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertNotNull;

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
import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.InvalidPathLenghtException;
import pt.tecnico.mydrive.exception.UnknownFileTypeException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class CreateFileTest extends AbstractServiceTest{
	
	private long token;
	private long token2;
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs = FileSystem.getInstance();
		User user = new User(fs, "Rita", "chocolate!", "12345678", new byte[] {1,1,1,1}, "home/chocolate!");
		LoginService service = new LoginService("chocolate!", "12345678");
		service.execute();
		this.token =  service.getToken();
		Login login = user.getLoginbyToken(service.getToken());
		Directory d = new Directory(login.getDirectory(), "exemplo", user, 20000, date); 
		PlainFile file = new PlainFile(login.getDirectory(), "textfile", login.getUser(), 50, date, "hey");
		App app = new App(login.getDirectory(), "apptest", login.getUser(), 51, date, "adeus ana");
		Link link = new Link(login.getDirectory(), "linktest", login.getUser(), 51, date, "link");
		
		User user2 = new User(fs, "Joana", "joana94", "joana1994", new byte[] {0,0,0,0}, "home/joana");
	}
	
	@Test
	public void success(){
		FileSystem fs =FileSystem.getInstance();
		
		CreateFileService createText = new CreateFileService(this.token, "text", "PlainFile", "teste1");
		createText.execute();
		
		Entity f = fs.getUserByUsername("chocolate!").getHome().getByName("text");
        assertNotNull("Text file was created", f);
   
        CreateFileService createDir = new CreateFileService(this.token, "dir", "Directory", null);
		createDir.execute();
		
		Entity d = fs.getUserByUsername("chocolate!").getHome().getByName("dir");
        assertNotNull("Directory was created", d);
        
        CreateFileService createApp = new CreateFileService(this.token, "app", "App", "appteste");
		createApp.execute();
		
		Entity a = fs.getUserByUsername("chocolate!").getHome().getByName("app");
        assertNotNull("App was created", a);
        
        CreateFileService createLink = new CreateFileService(this.token, "link", "Link", "linkteste");
		createLink.execute();
		
		Entity l = fs.getUserByUsername("chocolate!").getHome().getByName("link");
        assertNotNull("Link was created", l);
	}
	
	@Test(expected = UnknownFileTypeException.class)
	public void createInvalidTypeFile(){
		final String typefile = "plain";
		CreateFileService service = new CreateFileService(this.token, "exemplo2", typefile, "ola!");
		service.execute();
	}
	
	@Test(expected = ContentCannotBeNullException.class)
	public void createNullContentFile(){
		final String content = null;
		final String typefile = "Link";
		CreateFileService service = new CreateFileService(this.token, "exemplo3", typefile, content);
		service.execute();
	}
	
	/*
	@Test(expected = UserHasInvalidPermissionsException.class)
	public void createWithoutPermissionsFile(){
		
	}*/
	
	@Test(expected = InvalidPathLenghtException.class)
	public void createTooLongPathFile(){
		final String fileName = "Zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
				+ "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		final String content = "home/chocolate!/example";
		final String typeFile = "Link";
		CreateFileService service = new CreateFileService(this.token, fileName, typeFile, content);
		service.execute();
	}

/*	@Test(expected = DirectoryCannotHaveContentException.class)
	public void createNullDirectory(){
		 final String content = "oi";
		 final String typefile = "Directory";
		 CreateFileService service = new CreateFileService(this.token, "dirExe", typefile, content);
		 service.execute();
	}*/
	
}
