package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;
public class App extends App_Base {
    
    public App(FileSystem filesystem, String filename, User user, long id, DateTime lastModified, int dimension, String content) {
        super();
	initPlainFile(filesystem,filename,user,id,lastModified,dimension,content);
    }
    
    public App(FileSystem filesystem, Element xml){
    	xmlImport(xml);
    	setFilesystem(filesystem);
    }
    
    public void xmlImport(Element appDoc){
    	super.xmlImport(appDoc);
    }
    
    public Element xmlExport(){
    	return super.xmlExport();
    	
    }
}
