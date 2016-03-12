package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.joda.time.DateTime;

public class Link extends Link_Base {
    
    public Link(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, String content) {
        super();
    }
    
    public Link(FileSystem filesystem, Document xml){
    	xmlImport(xml);
    	setFilesystem(filesystem);
    }
    
    public void xmlImport(Document linkDoc){
    	super.xmlImport(linkDoc);
    }
    
    public Document xmlExport(){
    	Document linkDoc = super.xmlExport();
    	return linkDoc;
    }
}