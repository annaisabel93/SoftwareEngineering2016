package pt.tecnico.myDrive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joda.time.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.domain.*;


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
        
        User user;
        User usernew;
    	Directory d;
    	Directory dir;
    	Entity f;
    	Entity entity;
    	DateTime data = new DateTime();
    	log.trace("new FileSyste3");
    	new Entity();
        if (!fs.getUserSet().isEmpty()) return;
        
        else{
        	log.trace("new FileSystem4");
        	usernew = new User(fs, "maria","maria", "***", null, "home/maria");
        	/*if(fs.getUserSet().contains(usernew)){
        		log.trace("sim eu tenho a maria");
        	}
        	else{
        		log.trace("eu nao tenho a maria");
        	}*/
        	user = new User(fs, "joao","joao", "***", null, "home");
        	d = new Directory(user,fs,"home","cenas",user.getUserName(), 0 ,2, null);
        	dir = new Directory(usernew,fs,"home","cenas",usernew.getUserName(), 0 ,2, null);
        	log.trace("new FileSystem5");
        	f = new Entity(fs, d, "/home/homee","home","root",0, 2);
        	entity = new Entity(fs, dir, "/home/homee","home","root",0, 2);
        	log.trace("new FileSystem6");
        	new Entity();
        	log.trace("new FileSystem7");
        	return;
        }
    }

    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance();

        for (User usr: fs.getUserSet()) {
            System.out.println(usr.getUserName() + usr.getName() + usr.getHomeDir());
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