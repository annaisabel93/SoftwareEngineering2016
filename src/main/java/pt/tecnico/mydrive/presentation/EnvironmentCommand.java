package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.service.dto.VariableDto;
import pt.tecnico.mydrive.service.AddVariableService;

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
		if (args.length < 1) {
			service = new AddVariableService(token);
			service.execute();
			for ( VariableDto v : service.result() ) {
				System.out.println(v.getName() + " = " + v.getValue());
			}
		}	
						
	}
	








}
