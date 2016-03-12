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
	    //for (String s: args) xmlScan(new File(s));
		//print();
	    xmlPrint(); 
	    
	} finally { FenixFramework.shutdown(); }
    }
/*
    @Atomic
    public static void init() {
        log.trace("Init: " + FenixFramework.getDomainRoot());
        FileSystem.getInstance().cleanup();
    }
*/
    @Atomic
    public static void setup() {
        log.trace("Setup: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance(); 
    }
/*
    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance();

        for (Person p: fs.getPersonSet()) {
            System.out.println("The Contact book of " + p.getName() + " contains " + p.getContactSet().size() + " contacts :");
            for (Contact c: p.getContactSet())
            	System.out.println("\t" + c.getName() + " -> " + c.getPhoneNumber());
        }
    }
*/
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
/*
    @Atomic
    public static void xmlScan(File file) {
        log.trace("xmlScan: " + FenixFramework.getDomainRoot());
        FileSystem fs = FileSystem.getInstance();
        SAXBuilder builder = new SAXBuilder();
        try {
        	Document document = (Document)builder.build(file);
        	fs.xmlImport(document.getRootElement());
        } catch (JDOMException | IOException e) { e.printStackTrace(); }
    }
*/
}