package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ChangeDirectoryService;

public class ChangeWorkingDirCommand extends MyDriveCommand {
	
	private Shell shell;

    public ChangeWorkingDirCommand(Shell sh){
    	super(sh, "cwd", "Change Working Directory");
    	this.shell = sh;
    }
    public void execute(String[] args) {
		if (args.length < 1 || args.length > 1){
		    throw new RuntimeException("USAGE: "+name()+" <path> ");
		}
		else{
		   ChangeDirectoryService changing = new ChangeDirectoryService(shell.getToken(), args[0]);
		   changing .execute();
		   String actualPath = changing.getResult();
		   System.out.println(actualPath);
		}
    }
}
