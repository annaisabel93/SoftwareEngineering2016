package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Link extends Link_Base {
    
    public Link(FileSystem filesystem, String filename, User user, long id, DateTime lastModified, int dimension, String content) {
        super();
	initPlainFile(filesystem,filename,user,id,lastModified,dimension,content);
    }


    public String getMyType(){
	return "Link";
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
