package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ChangeDirectoryService;
import pt.tecnico.mydrive.service.KeyService;
import pt.tecnico.mydrive.service.ListDirectoryService;
import pt.tecnico.mydrive.service.LoginService;

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
			KeyService gus = new KeyService(token);
			gus.execute();
			System.out.println("Token: " + gus.getToken() + "; Username : " + gus.getUsername());
		} else if(args.length == 1) {      //altera o utilizador atual, atualiza o token ativo e imprime o seu valor
			KeyService gus = new KeyService(token);
			gus.execute();
			LoginService ls = new LoginService(args[0], gus.getPassword());
			ls.execute(); 
			System.out.println("Token: " + ls.getToken() + "; Username : " + args[0]);
		} else {
			throw new RuntimeException("USAGE: [username]");
		}
		
	}

}
