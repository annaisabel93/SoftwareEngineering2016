package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Link extends Link_Base {
    
    public Link(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, String content) {
        super();
	initPlainFile(filesystem,filename,owner,id,lastModified,dimension,content);
    }
    
    public Link(FileSystem filesystem, Element xml){
    	xmlImport(xml);
    	setFilesystem(filesystem);
    }
    
    public void xmlImport(Element linkDoc){
    	super.xmlImport(linkDoc);
    }
    
    public Element xmlExport(){
    	return super.xmlExport();
    
    }
}
