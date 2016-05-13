package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ExecuteFileService;

public class ExecuteCommand extends MyDriveCommand {
	
	private Shell sh;

	public ExecuteCommand (Shell sh) { 
		super(sh, "do", "executes file in indicated path"); 
		this.sh = sh;
	}

	public void execute (String[] args) {
		long token = sh.getToken();
		if ( args.length < 1 ) {
			throw new RuntimeException("USAGE: <do> <path> [<args>}");
		}
		else if ( args.length == 1 ) {
			//new ExecuteFileService(token, args[0], null).execute();
		}
		else {
			//new ExecuteFileService(token, args[0], args[1]).execute();
		}

	}




}
