package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Directory;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


public class DeleteFileTest extends AbstractServiceTest {
	
	protected void populate(){
		FileSystem fs =FileSystem.getInstance();
		
	 	     fs.adicionaUser("luis");
	         fs.login("luis");
	         fs.moveDir("..");
	         fs.CreateTextFile("README");
	         fs.WriteOnFile("README","lista de utilizadores");
	         fs.moveDir("..");
	         fs.AddDirtoCurrent("usr");
	         fs.moveDir("usr");
	         fs.AddDirtoCurrent("local");
	         fs.moveDir("local");
	         fs.AddDirtoCurrent("bin");
	         fs.moveDir("..");
	         fs.moveDir("..");
	         fs.moveDir("home");
	         fs.printReadMe("README");
	         fs.moveDir("..");
	         fs.moveDir("usr");
	         fs.moveDir("local");
	         fs.RemoveDir("bin");
	         fs.moveDir("..");
	         fs.moveDir("..");
	         fs.moveDir("home");
	         fs.RemoveFile("README");

	}
	

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
