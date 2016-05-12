package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;

public class Link extends Link_Base {
    
    public Link(Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
	super();
	init(dir, filename,user,id,lastModified);
        checkLink(content);
        setContent(content);
    }

    public Link(User owner, Element xml){
    	xmlImport(xml);
    	setOwner(owner);
    }
    
    public int getSize(){
    	return getContent().length();
    }
    
    public String checkType(){
    	return "link";
    }
    
    public String execute(){
    	return getContent();
    }
    
    public void checkLink(String content) throws ContentCannotBeNullException {
    	if ( content.equals(null) ) 
		throw new ContentCannotBeNullException(getContent()); 
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
		
		if (getOwner().getUserName().equals("root")){ 	//FIXME
			element.addContent(new Element("perm").setText("----")); //dirty hack, can't have mask working
		}
		
		return element;
    
    }
}
