package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.joda.time.DateTime;
public class App extends App_Base {
    
    public App(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, String content) {
        super();
    }
    
    public App(FileSystem filesystem, Document xml){
    	xmlImport(xml);
    	setFilesystem(filesystem);
    }
    
    public void xmlImport(Document appDoc){
    	super.xmlImport(appDoc);
    }
    
    public Document xmlExport(){
    	Document appDoc = super.xmlExport();
    	return appDoc;
    }
}
