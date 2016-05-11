package pt.tecnico.mydrive.presentation;

public class KeyCommand extends Command {
	
	private Shell sh;
	
	public KeyCommand(Shell sh) {
    	super(sh, "token", "allows to have multiple users in session");
    	this.sh = sh;
    }
	
	public void execute(String[] args) {
		
		
	}

}
