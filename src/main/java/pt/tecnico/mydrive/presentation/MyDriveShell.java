package pt.tecnico.mydrive.presentation;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;

public class MyDriveShell extends Shell {

  public static void main(String[] args) throws Exception {
    MyDriveShell sh = new MyDriveShell();
    sh.execute();
  }

  public MyDriveShell() { // Adicionem aqui os comandos correspondentes a cada coisa da apresentacao
	    super("MyDrive");
//	    try {
//			FenixFramework.getTransactionManager().begin(false);
//		} catch (WriteOnReadError | NotSupportedException | SystemException e) {
//			e.printStackTrace();
//		}
	    new LoginCommand(this);
	    new ChangeWorkingDirCommand(this);
	    new List(this);
	    new WriteCommand(this);
	    new KeyCommand(this);
	    new ExecuteCommand(this);
	    new EnvironmentCommand(this);
  }
}
