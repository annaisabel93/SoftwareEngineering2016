package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;

public class CreateFileTest extends AbstractServiceTest{
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs = FileSystem.getInstance();
		User user = new User(fs, "Rita", "rita", "rita94", null, "home/rita");
		Entity e = new Directory(fs.getWorkDir(), "exemplo", user, 20000, date); //FIXME-- apenas um esboco ainda
	
	}
	
	private Entity getEntity(String username, String fileName){
		User user = FileSystemService.getFileSystem().getUserByUsername(username); 
		return user.getEntityByName(fileName);
	}
	/*
	@Test
	public void success(){
		final String fileName = "example";
		final String username = "rita";
		long token = 0; //FIXME--retirar parametro
		CreateFileService service = new CreateFileService(token, fileName, "Directory"); 
		service.execute();
		
		Entity e = getEntity(username, fileName);
		assertNotNull("file was not created", e);
		assertEquals("invalid filename", "example", e.getFilename());
	}*/
	/*
	@Test(expected = TexFileDoesNotExistException.class)
	public void invalidTextFileCreation(){
		final String filename = "plain";
		CreateFileService service = new CreateFileService();
		service.execute();
	}*/
	
}