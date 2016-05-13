package pt.tecnico.mydrive.service;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)

public class EnvironmentLinksTest {
	
	//pretend to solve things
	@Test
	public void resolve() {
		new MockUp<ResolveVariableService>() {
			@Mock
			String convert(String path) {
				assertEquals("path", path, "/home/$USER/chocolate");
				return "/home/Ana/chocolate"; }
		};

		ResolveVariableService r = new ResolveVariableService("/home/$USER/chocolate");
		assertEquals("Resolved", r.convert("/home/$USER/chocolate"), "/home/Ana/chocolate");
	} 
}