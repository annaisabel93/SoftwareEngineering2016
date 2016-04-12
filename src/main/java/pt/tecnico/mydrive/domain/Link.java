package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

public class Link extends Link_Base {
    
    public Link(Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
        super();
        init(dir, filename,user,id,lastModified);
        setContent(content);
    }

    public Link(User owner, Element xml){
    	xmlImport(xml);
    	setOwner(owner);
    }
    
    
    
    public String checkType(){
    	return "link";
    }
    
    public void xmlImport(Element linkDoc){
    	super.xmlImport(linkDoc);
    	setContent(new String(linkDoc.getChild("content").getValue()));
    }
    
    public Element xmlExport(){
		Element element = new Element("link");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 
		element.addContent(new Element ("path").setText(getPath(getParent().toString())));
		element.addContent(new Element ("name").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner().toString()));
		element.addContent(new Element ("value").setText(getContent()));
		
		//TODO Perm
		
		return element;
    
    }
}
