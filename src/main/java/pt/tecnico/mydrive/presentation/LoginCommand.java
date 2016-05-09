package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;
import pt.tecnico.mydrive.domain.Login;

public class LoginCommand extends MyDriveCommand {

	private Shell shell;

	public LoginCommand(Shell sh) {
		super(sh, "login", "Log in the System");
		this.shell = sh;
	}

	public void execute(String[] args) {
		if (args.length < 2) {
			throw new RuntimeException("USAGE: " + name() + " <username> <password> ");
		}
		if (args.length > 1) {
			LoginService service = new LoginService(args[0], args[1]);
			service.execute();

			long token = service.getToken();
			this.shell.setToken(token);
		}
	}
}
