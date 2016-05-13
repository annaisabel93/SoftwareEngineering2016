package pt.tecnico.mydrive.system;

import org.junit.Test;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.presentation.ChangeWorkingDirCommand;
import pt.tecnico.mydrive.presentation.EnvironmentCommand;
import pt.tecnico.mydrive.presentation.ExecuteCommand;
import pt.tecnico.mydrive.presentation.KeyCommand;
import pt.tecnico.mydrive.presentation.ListCommand;
import pt.tecnico.mydrive.presentation.LoginCommand;
import pt.tecnico.mydrive.presentation.MyDriveShell;
import pt.tecnico.mydrive.presentation.WriteCommand;
import pt.tecnico.mydrive.service.AbstractServiceTest;

public class SystemTest extends AbstractServiceTest{
	private MyDriveShell sh;
	
	@Override
	protected void populate() {
		sh = new MyDriveShell();
		PlainFile file = new PlainFile();
		
	}
	 @Test
	 public void success(){
		 new LoginCommand(sh).execute(new String[] { "root", "***"});
	     new ListCommand(sh).execute(new String[] { "/home" } );
	     new ChangeWorkingDirCommand(sh).execute(new String[] { "/home" } );
	    new ExecuteCommand(sh).execute(new String[] { "/home/ana/chocolate/" , "cafe"} );
	     
	     //ver como se coloca o file
//	     new WriteCommand(sh).execute(new String[] {"/home/file", "ola" } );
//	     new EnvironmentCommand(sh).execute(new String[] { "Sofia" } );
//	     new KeyCommand(sh).execute(new String[] { } );
	 }
}
