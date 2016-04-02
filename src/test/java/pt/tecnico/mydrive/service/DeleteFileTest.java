package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.User;


public class DeleteFileTest {
	
	protected void populate(){
		FileSystem fs =FileSystem.getInstance();
		
		byte[] mask = {0, 0, 0, 0};
		Directory homeDir;
		User usr = new User(fs, "Ana", "anna", "1234", mask, null);
		//long id = homeDir.getId();
		DateTime lastModified;
		
		//Directory d = new Directory(fs, null, homeDir.getFilename(), usr, id, lastModified);
	}
	/*
	private Directory getDirectory() {
		
	}
	
	
	@Test
	public void sucess(){
		
	}*/
}
