package pt.tecnico.mydrive.service;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Variable;

public class AddVariableTest extends AbstractServiceTest {

	private static final String name = "Ana";
	private static User user;
	private Login login;
	private static final String value = "Ana";
	private long token;
	private Variable variable;

	private FileSystem fs;

	protected void populate() {

		fs = FileSystem.getInstance();

		//variable = new Variable(login, "Bananas", value);
		User user = new User(fs, "Ana", "anna", "12345678", new byte[] {1,1,1,1}, "/home/ana");
		
		LoginService service = new LoginService("anna", "12345678");
		service.execute();
		
		this.token =  service.getToken();
		
		Login login = user.getLoginbyToken(service.getToken());
		this.login = login;	
		
		variable = new Variable(login, "Bananas", value);
		this.variable = variable;
		
	}


	@Test
	public void success() {

		FileSystem fs =FileSystem.getInstance();
		
		LoginService service = new LoginService("anna", "12345678");
		service.execute();

		Variable result = login.getVariableByName("Bananas");
		
		

		AddVariableService add = new AddVariableService(token, variable.getName(), value);
		add.execute();
		assertEquals(result.getName(), variable.getName());

	}
	
}
