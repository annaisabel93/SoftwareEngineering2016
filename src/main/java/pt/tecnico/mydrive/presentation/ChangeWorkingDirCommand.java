package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ChangeDirectoryService;

public class ChangeWorkingDirCommand extends MyDriveCommand {

    public ChangeWorkingDirCommand(Shell sh){
    	super(sh, "add", "Change Working Directory");
    }
    public void execute(String[] args) {
		if (args.length < 1){
		    throw new RuntimeException("USAGE: "+name()+" <path> ");
		}
		if (args.length > 0){
		   // new ChangeDirectoryService(args[0]).execute();
		}
    }
}
