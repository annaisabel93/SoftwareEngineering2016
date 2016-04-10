package pt.tecnico.mydrive;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joda.time.DateTime;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.User;


public class Main {
    static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {
        System.out.println("*** Welcome to the My Drive application! ***");
	try {
	    
	    setup();
	    for (String s: args) xmlScan(new File(s));
	    	print();
	    	xmlPrint(); 
	    
	} finally { FenixFramework.shutdown(); }
    }

    @Atomic
    public static void init() {
        log.trace("Init: " + FenixFramework.getDomainRoot());
        FileSystem.getInstance().cleanup();
    }

    @Atomic
    public static void setup() {
        log.trace("Setup: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance(); 
        //User userana;
    	//Directory dirana;
    	//DateTime date = new DateTime();

    	//userana = new User(fs, "ana", "ana", "***", null, "home");
    	//FIX TODO last null arg should be date
    	//dirana = new Directory(fs, null,  "cenas", userana, 0, date);
       //---------------------------------------------------------------------------------------------
        User usr = new User(fs, "Ana", "chocolate!", "1234", new byte[] {0,0,0,0} , "/home/chocolate");
        fs.login("luis");
        fs.moveDir("..");
        fs.CreateTextFile("README");
        fs.WriteOnFile("README","lista de utilizadores");
        fs.moveDir("..");
        fs.AddDirtoCurrent("usr");
        fs.moveDir("usr");
        fs.AddDirtoCurrent("local");
        fs.moveDir("local");
        fs.AddDirtoCurrent("bin");
        fs.moveDir("..");
        fs.moveDir("..");
        fs.moveDir("home");
        fs.printReadMe("README");
        fs.moveDir("..");
        fs.moveDir("usr");
        fs.moveDir("local");
        fs.moveDir("..");
        fs.moveDir("..");
        fs.moveDir("home");
  
        fs.printHome();
        
    }

    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance();

        for (User usr: fs.getUserSet()) {
           // System.out.println(usr.getUserName() + usr.getName() + usr.getHomeDir());
            /*for (Directory d: usr.get)
            	System.out.println("\t" + ent.getFilename() + ent.getOwner());*/
        }
    }

    @Atomic
    public static void xmlPrint() {
        log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
        Document doc = FileSystem.getInstance().xmlExport();
        XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
        try { 
        	xmlOutput.output(doc, new PrintStream(System.out));
        } 
        catch (IOException e) { System.out.println(e); }
    }

    @Atomic
    public static void xmlScan(File file) {
        log.trace("xmlScan: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance();
        SAXBuilder builder = new SAXBuilder();
        try {
        	Document document = (Document)builder.build(file);
        	fs.xmlImport(document);
        } catch (JDOMException | IOException e) { e.printStackTrace(); }
    }

}
