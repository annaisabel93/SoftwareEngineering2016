package pt.tecnico.mydrive.domain;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;
public class App extends App_Base {
    
    public App(FileSystem filesystem, Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
        super();
	initPlainFile(filesystem,dir, filename,user,id,lastModified,content);
    }

	    
	public String getMyType(){
		return "App";
	}

    public App(User owner, Element xml){
    	xmlImport(xml);
    	setOwner(owner);
    }
    
    public void xmlImport(Element appDoc){
    	super.xmlImport(appDoc);
    }
    
    public Element xmlExport(){
    	return super.xmlExport();
    	
    }
}
