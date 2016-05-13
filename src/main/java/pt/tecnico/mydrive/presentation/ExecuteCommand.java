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
//		else if ( args.length == 1 ) {
//			new ExecuteFileService(token, args[0], null).execute();
//		}
		else {
		 
			String[] argms = new String[args.length - 1];
			int a = 1;
			for ( int i = 0; i < args.length - 1 ; i++ ) { 
				argms[i] = args[i + a];
			}
			new ExecuteFileService(token, args[0], argms).execute();
		}

	}




}
