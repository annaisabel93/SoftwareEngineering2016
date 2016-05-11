package pt.tecnico.mydrive.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import mockit.internal.util.MockInvocationHandler;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.service.dto.VariableDto;


@RunWith(JMockit.class)
public class EnvironmentLinksTest extends AbstractServiceTest {

	private ChangeDirectoryService service;
	private static Link link1;
	private static final String name = "Ana";
	private static User user;
	private Login login;
	private static String value;
	private long token;
	private Variable variable;
	private Variable variable2;
	private long id;
	private DateTime lastModified;
	private String content;
	private List<Variable> variables;
	private String total;
	
	private FileSystem fs;
	private long token2;
	private Login login2;
	private String value2;
	private DateTime date;
	
	protected void populate() {
		
		fs = FileSystem.getInstance();
		
		User user = new User(fs, "Ana", "anna", "12345678", new byte[] {1,1,1,1}, "/home/ana");
		LoginService service = new LoginService("anna", "12345678");
		service.execute();
		
		Directory dir = new Directory(login.getDirectory(), "dir", login.getUser(), 20000, date);
		Link link = new Link(dir, "link", user, id, lastModified, content);
		Variable var = new Variable(login, name, value);
		
		this.token = service.getToken();	
		this.login = service.getLogin(this.token);
		
		ReplaceVariableService replace = new ReplaceVariableService(token, link, var);
			
		
		
		//populate mockup
		variables.add(variable);
		variables.add(variable2);
		
		
	}
	
	@Test
	public void success(){
		
		new MockUp<ReplaceVariableService>() {
			@Mock	
			List<Variable> result(){ return variables; }
			
		};
		
//mock do método que recebe um path (com environment links pelo meio) e retorna o caminho verdadeiro (o que lá está dentro)
		
		
        

//		
//		assertTrue(service.getResult().contains(variable.getValue()));
	}
	

}
