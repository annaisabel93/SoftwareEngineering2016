package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;
public class App extends App_Base {
    
    public App(FileSystem filesystem, Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
        super();
        init(filesystem,dir, filename,user,id,lastModified);
        setContent(content);
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
