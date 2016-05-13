package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.CreateFileService;
import pt.tecnico.mydrive.service.WriteFileService;

public class WriteCommand extends Command {

	private Shell sh;
	
	public WriteCommand(Shell sh) {
		super(sh, "update", "writes text to a file (given the path of that same file).");
		this.sh = sh;
	}

	public void execute(String[] args) {
		long token = sh.getToken(); 

		if (args.length == 2) {
			
			// Parses the file path to retrieve the exact filename
			String path = args[0];
			String delims = "/";
			String[] tokens = path.split(delims);
			int filenameIndex = tokens.length - 1;
			String filename = tokens[filenameIndex];
			
			//Creates the file
			CreateFileService cfs = new CreateFileService(token, filename, "PlainFile", "old");
			cfs.execute();
			
			// Then tries to update its content
			WriteFileService wfs = new WriteFileService(token, args[0], args[1]);
			wfs.execute();
			
		} else {
			throw new RuntimeException("USAGE: <update> <path> <text>");
		}
	}
}
