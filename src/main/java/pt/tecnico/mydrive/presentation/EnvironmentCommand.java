package pt.tecnico.mydrive.presentation;


import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.service.dto.VariableDto;
import pt.tecnico.mydrive.service.AddVariableService;
import pt.tecnico.mydrive.service.ListVariableService;

import java.util.List;

public class EnvironmentCommand extends MyDriveCommand {

	private Shell sh;
	
	public EnvironmentCommand (Shell sh) { 
		super(sh, "env", "creates or modifies an environment variable");
		this.sh = sh;
	}
	
	public void execute (String[] args) {
		long token = sh.getToken();
		AddVariableService service;
		ListVariableService serviceList;
		if (args.length == 0) {
			//service = new AddVariableService(sh.getToken(), null, null);
			//service.execute();
			serviceList = new ListVariableService(sh.getToken(), null, null);
			serviceList.execute();
			//System.out.println("Bys");
			
		}
/*		else if (args.length == 1) {
			service = new AddVariableService(sh.getToken(), args[0], null);
			service.execute();
			serviceList = new ListVariableService(sh.getToken(), args[0], null);
			serviceList.execute();
			

			//System.out.println(args[0] + " = " + service.getAssociatedValue());	
		}
		else if ( args.length == 2) {
			new AddVariableService(sh.getToken(), args[0], args[1]).execute();
			serviceList = new ListVariableService(sh.getToken(),args[0], args[1]);
			serviceList.execute();
			

			//System.out.println(args[0] + " = " + args[1]);
		}*/
						
	}
	



}
