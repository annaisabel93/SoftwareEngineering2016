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
			ListDirectoryService lds = new ListDirectoryService(token);
			//lds.execute();
		} else if(args.length == 1) {
			ChangeDirectoryService cds = new ChangeDirectoryService(token, args[0]);
			//cds.execute();
			ListDirectoryService lds = new ListDirectoryService(token);
			//lds.execute();
		} else {
			throw new RuntimeException("USAGE: [path]");
		}
	}

	
}
