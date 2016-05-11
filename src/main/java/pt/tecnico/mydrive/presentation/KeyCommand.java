package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ChangeDirectoryService;
import pt.tecnico.mydrive.service.ListDirectoryService;

public class KeyCommand extends Command {
	
	private Shell sh;
	
	public KeyCommand(Shell sh) {
    	super(sh, "token", "allows to have multiple users in session");
    	this.sh = sh;
    }
	
	public void execute(String[] args) {

		long token = sh.getToken();  //token bem feito
		
		//imprime o valor do token e o username do utilizador atual
		if (args.length == 0) {
			
		} else if(args.length == 1) {      //altera o utilizador atual, atualiza o token ativo e imprime o seu valor
			
		} else {
			throw new RuntimeException("USAGE: [username]");
		}
		
	}

}
