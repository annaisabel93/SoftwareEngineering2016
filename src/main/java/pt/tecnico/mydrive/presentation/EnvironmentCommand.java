package pt.tecnico.mydrive.presentation;

import java.util.List;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.service.dto.VariableDto;
import pt.tecnico.mydrive.service.AddVariableService;
import pt.tecnico.mydrive.service.ListVariableService;

public class EnvironmentCommand extends MyDriveCommand {

	private Shell sh;
	
	public EnvironmentCommand (Shell sh) { 
		super(sh, "env", "creates or modifies an environment variable");
		this.sh = sh;
	}
	
	public void execute (String[] args) {
		long token = sh.getToken();
		AddVariableService service;
		
		if (args.length == 0) {
			service = new AddVariableService(token, null, null);
			service.execute();
			for ( VariableDto v : service.result()) { 
				System.out.println(v.getName() + " = " + v.getValue()); 
			} 
		}
		else if (args.length == 1) {
			service = new AddVariableService(token, args[0], null);
			service.execute();
			
			for ( VariableDto v : service.result() ) {
				if ( v.getName().equals(args[0]) ) 
					System.out.println(v.getName() + " = " + v.getValue()); 
			} 

		}
		else if ( args.length == 2) {
			service = new AddVariableService(sh.getToken(), args[0], args[1]);
			service.execute();
			
			System.out.println(args[0] + " = " + args[1]);
		}
						
	}
	



}
