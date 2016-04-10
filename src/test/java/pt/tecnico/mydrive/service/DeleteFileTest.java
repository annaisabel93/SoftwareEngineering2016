package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Entity;

import static org.junit.Assert.assertFalse;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.service.LoginService;

import org.junit.Test;


public class DeleteFileTest extends AbstractServiceTest {
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Ana", "chocolate!", "1234", new byte[] {0,0,0,0} , "/home/chocolate");
		new LoginService(fs, 1, "chocolate!", "1234");
		Login login = usr.getLoginbyToken(1);
		Entity e = new Directory(fs.getWorkDir(), "exemplo", usr, 20000, date);
		
		
		
	}
	//login
	//criar ficheiro
	//apagar ficheiro
	//escrever ficheiro

	/*
	@Test
	public void sucess(){
		final String DirName = "home";
		// DeleteFileService service = new DeleteFileService(DirName);
		// service.execute();
		
	}
	
	@Test
	
	public void sucessRemoveFileText(){
		final String FileName = "README";
		 DeleteFileService service = new DeleteFileService(DirName);
		 service.execute();
		
        // check file was removed
        assertFalse("file was not removed", mydriveservice.(...).(...));
		
		
	} */
}
