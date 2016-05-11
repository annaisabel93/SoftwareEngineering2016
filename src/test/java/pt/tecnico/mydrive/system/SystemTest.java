package pt.tecnico.mydrive.system;

import org.junit.Test;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.presentation.LoginCommand;
import pt.tecnico.mydrive.presentation.MyDriveShell;
import pt.tecnico.mydrive.service.AbstractServiceTest;

public class SystemTest extends AbstractServiceTest{
	private MyDriveShell sh;
	
	@Override
	protected void populate() {
		sh = new MyDriveShell();
		
	}
	 @Test
	 public void sucess(){
		 new LoginCommand(sh).execute(new String[] { "Ana"});
	 }
}
