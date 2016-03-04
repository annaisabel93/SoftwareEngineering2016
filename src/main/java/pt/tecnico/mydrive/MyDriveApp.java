package pt.tecnico.mydrive;

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


public class MyDriveApp {
    static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {
        System.out.println("*** Welcome to the My Drive application! ***");
	/*try {
	    setup();
	    for (String s: args) xmlScan(new File(s));
		print();
	    xmlPrint();
	} finally { FenixFramework.shutdown(); }*/
    }

 /*   @Atomic
    public static void init() { // empty phonebook
        log.trace("Init: " + FenixFramework.getDomainRoot());
	PhoneBook.getInstance().cleanup();
    }

    @Atomic
    public static void setup() { // phonebook with debug data
        log.trace("Setup: " + FenixFramework.getDomainRoot());
	PhoneBook pb = PhoneBook.getInstance();

        
    }

    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
	PhoneBook pb = PhoneBook.getInstance();

        for (Person p: pb.getPersonSet()) {
            System.out.println("The Contact book of " + p.getName() + " contains " + p.getContactSet().size() + " contacts :");
	    for (Contact c: p.getContactSet())
		System.out.println("\t" + c.getName() + " -> " + c.getPhoneNumber());
	}
    }

    @Atomic
    public static void xmlPrint() {
        log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
	Document doc = PhoneBook.getInstance().xmlExport();
	XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
	try { xmlOutput.output(doc, new PrintStream(System.out));
	} catch (IOException e) { System.out.println(e); }
    }

    @Atomic
    public static void xmlScan(File file) {
        log.trace("xmlScan: " + FenixFramework.getDomainRoot());
	PhoneBook pb = PhoneBook.getInstance();
	SAXBuilder builder = new SAXBuilder();
	try {
	    Document document = (Document)builder.build(file);
	    pb.xmlImport(document.getRootElement());
	} catch (JDOMException | IOException e) {
	    e.printStackTrace();
	}
    }*/
	
}