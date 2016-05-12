package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.domain.Variable;


@RunWith(JMockit.class)
public class EnvironmentLinksTest extends AbstractServiceTest {

	private ReplaceVariableService replace;
	private static final String name = "var";
	private static User user;
	private Login login;
	private static String value = "Ana";
	private long token = 0;
	private Variable variable;
	private long id;
	private DateTime lastModified;
	private static String content = "home/$USER/chocolate";
	private static final List<String> variables = new ArrayList<String>();
	private String total;
	
	private FileSystem fs;
	private DateTime date = new DateTime();
	private Link link;
	
	protected void populate() {
		
		fs = FileSystem.getInstance();
		
		User user = new User(fs, "Ana", "ana", "12345678", new byte[] {1,1,1,1}, "/home/ana");
		LoginService service = new LoginService("ana", "12345678");
		service.execute();
		
		this.token = service.getToken();	
		this.login = service.getLogin(this.token);
		
		

		//Directory dir = new Directory("dir", login.getUser(), 20000, date);
		Link link = new Link(login.getDirectory(), "link", user, id, lastModified, "/home/ana");
		Variable var = new Variable(login, name, value);
		this.variable = var;
		this.link = link;

		
			
		
		
		//populate mockup
		variables.add(var.getValue());
		
		
	}
	
	@Test
	public void success(){
		
		new MockUp<ReplaceVariableService>() {
			@Mock	
			List<String> getResult(){ return variables; }
			
		};
		
		
//mock do método que recebe um path (com environment links pelo meio) e retorna o caminho verdadeiro (o que lá está dentro)
//		long token2 = 12345;
//		System.out.println("\n\n\n\n olá \n\n\n\n\n\n\n" + (this.token) + (this.link==null) + (this.variable == null));
//		ReplaceVariableService replace = new ReplaceVariableService(token2, this.link, this.variable);
//		replace.execute();
//      
//        assertEquals("/home/Ana/chocolate", replace.getResult());
       
//		
//		assertTrue(service.getResult().contains(variable.getValue()));
	}
	

}
