package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.InexistentPointerForLinkException;

public class Link extends Link_Base {
    
    public Link(Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
	super();
	checkExistance(content, user);
	init(dir, filename,user,id,lastModified);     
        setContent(content);
    }

    public boolean checkExistance(String absolutePath, User user) throws ContentCannotBeNullException, InexistentPointerForLinkException { //ve se o ficheiro em questao existe
    	Directory root = user.getSystem().getRootDir(); //Starts on rootDir
    	if(absolutePath == null){
    		throw new ContentCannotBeNullException("nulo");
    	}
    	String[] items= absolutePath.split("/"); //splits the full path into Directories
    	
    	for(int x = 1; x < items.length; x++){ // Goes to each array position to move 1 by 1 until the last position (destiny)
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
			//receives dirname in constructor
			//in this case it is inexistent, so it will be null
    			throw new InexistentPointerForLinkException(null);
    		}
    		root = dir;
    	}
    	return true;
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
    
    @Override
    public String execute(){
    	String s = getContent();
    	s.split("/");
    	return getContent();
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
