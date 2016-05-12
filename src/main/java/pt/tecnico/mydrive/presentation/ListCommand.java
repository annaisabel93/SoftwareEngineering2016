package pt.tecnico.mydrive.presentation;

import java.math.BigInteger;
import java.util.Random;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.service.ChangeDirectoryService;
import pt.tecnico.mydrive.service.ListDirectoryService;
import pt.tecnico.mydrive.service.FileSystemService;
import pt.tecnico.mydrive.domain.Login;

public class ListCommand extends Command {

	private Shell sh;
	String originalPath;
	
	public ListCommand(Shell sh) {
    	super(sh, "ls", "lists the files on the current directory, or, if given the path of the directory, lists the files in that directory");
    	this.sh = sh;
    }

	public void execute(String[] args) {
		
		long token = sh.getToken(); 
		
		if (args.length == 0) {
			ListDirectoryService service1 = new ListDirectoryService(token);
			service1.execute();
			
		} else if(args.length == 1) {
			//falta guardar a path o valor da directoria actual
			
			ChangeDirectoryService service2 = new ChangeDirectoryService(token, args[0]);
			service2.execute();
			
	
			this.originalPath = service2.getOrigin();
			
			
			ListDirectoryService service3 = new ListDirectoryService(token);
			service3.execute();
			
			ChangeDirectoryService service4 = new ChangeDirectoryService(token, this.originalPath);
			service4.execute();
			
		} else {
			throw new RuntimeException("USAGE: [path]");
		}
	}


	
}
