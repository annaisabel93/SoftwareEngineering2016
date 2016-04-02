package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

public class Link extends Link_Base {
    
    public Link(FileSystem filesystem, Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
        super();
        init(filesystem, dir, filename,user,id,lastModified);
        setContent(content);
    }

    public Link(User owner, Element xml){
    	xmlImport(xml);
    	setOwner(owner);
    }
    
    public void xmlImport(Element linkDoc){
    	super.xmlImport(linkDoc);
    }
    
    public Element xmlExport(){
    	return super.xmlExport();
    
    }
}
