package pt.tecnico.mydrive.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format; 
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.service.*;
import pt.tecnico.mydrive.service.dto.*;
import pt.tecnico.mydrive.exception.*;


@RunWith(JMockit.class)
public class IntegrationTest extends AbstractServiceTest {
	
    private static final List<String> usernames = new ArrayList<String>();
    private static final String u1 = "Tiago", u2 = "Miguel", u3 = "Xana";
    private static final String u4 = "Antonio", u5 = "Ana";
    private static final String importFile = "mydrive.xml";
	
	@Override
	protected void populate() {
		usernames.add(u2);
		usernames.add(u3);
		
	}
	
	@Test
	public void success() throws MyDriveException{
//		new LoginService(u1, "12345678").execute();
//		new LoginService(u2, "olatecnico").execute();
//		
	}
	
}
