package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;
public class App extends App_Base {
    
    public App(Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
        super();
        init(dir, filename,user,id,lastModified);
        setContent(content);
    }

    public App(User owner, Element xml){
    	xmlImport(xml);
    	setOwner(owner);
    }
    
    public void xmlImport(Element appDoc){
    	super.xmlImport(appDoc);
    }
    
    public String checkType(){
    	return "app";
    }
    
    public Element xmlExport(){
		Element element = new Element("app");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 
		element.addContent(new Element ("path").setText(getPath(getParent().toString())));
		element.addContent(new Element ("name").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner().toString()));
		element.addContent(new Element ("method").setText(getContent()));
		
		return element;		
    	
    }
}
