package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.LoginService;

public class LoginCommand extends MyDriveCommand {

    public LoginCommand(Shell sh){
    	super(sh, "login", "Log in the System");
    }
    public void execute(String[] args) {
		if (args.length < 2){
		    throw new RuntimeException("USAGE: "+name()+" <username> <password> ");
		}
		if (args.length > 1){
		   new LoginService(args[0], args[1]);
		}
    }
}
