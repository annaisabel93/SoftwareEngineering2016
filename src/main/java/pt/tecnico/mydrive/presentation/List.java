package pt.tecnico.mydrive.presentation;

import java.math.BigInteger;
import java.util.Random;


import pt.tecnico.mydrive.service.ChangeDirectoryService;
import pt.tecnico.mydrive.service.ListDirectoryService;

public class List extends Command {

	private Shell sh;
	
	public List(Shell sh) {
    	super(sh, "ls", "lists the files on the current directory, or, if given the path of the directory, lists the files in that directory");
    	this.sh = sh;
    }

	public void execute(String[] args) {
		
		long token = sh.getToken();  //token bem feito
		
		if (args.length == 0) {
			ListDirectoryService service1 = new ListDirectoryService(token);
			service1.execute();
			
		} else if(args.length == 1) {
			//falta guardar o valor da directoria actual
			ChangeDirectoryService service2 = new ChangeDirectoryService(token, args[0]);
			service2.execute();
			
			ListDirectoryService service3 = new ListDirectoryService(token);
			service3.execute();
		} else {
			throw new RuntimeException("USAGE: [path]");
		}
	}

	
}
