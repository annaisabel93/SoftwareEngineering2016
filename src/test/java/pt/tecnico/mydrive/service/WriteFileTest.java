package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.tecnico.mydrive.Main;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;

public class WriteFileTest {

	protected void populate() {
		
		FileSystem fs = FileSystem.getInstance();
		
		byte[] mask = {1, 1, 0, 0};
		String username = "filipac";
		String name = username;
		String password = username;
		String homeDir = "/home/" + username;
		User user = new User(fs, name, username, password, mask, homeDir);
		
		//Token token = new Token();
		Long token = 100L;
		
		String filename = "testFile";
		long id = 1000L;
		DateTime lastModified = new DateTime();
		String content = "content";
		PlainFile file = new PlainFile(fs.getUserDir(username), filename, user, id, lastModified, content);
		
	}
	
	@Test
	public void success() {
//		WriteFileService service = new WriteFileService(100L, "testFile", "testContent");
//		service.execute;
//		PlainFile file = service.result();
//		
//		assertEquals(file.getContent(), "testContent");
	}
	
//	@Test(expected = TexFileDoesNotExistException.class)
//	public void invalidWriteFileWithNonexistingFilename() {
//		WriteFileService service = new WriteFileService(101L, "noFile", "content");
//		service.execute();
//	}
	
	//Se utilizador nao tiver permissoes para escrever no ficheiro dado (tb falta criar a respectiva excepcao)
}
