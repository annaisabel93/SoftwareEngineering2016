package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile(Directory parent,String filename, User user, long id, DateTime lastModified,  String content) {
        super();
        init(parent,filename,user,id,lastModified);
		setContent(content);
        
    }
    

	public PlainFile() {
		// TODO Auto-generated constructor stub
	}
	
	public PlainFile(User owner, Element xml){
		xmlImport(xml);
		setOwner(owner);
	}
	public void addContent(Login login, String content){
		if (checkPermissions(login, "write") == false)	
			throw new UserHasInvalidPermissionsException();
		setContent(content);
	}
	
	@Override
	public void delete(){
		getOwner().removeFile(this);
		setParent(null);
		setOwner(null);
	}

	public void xmlImport(Element PlainFileDoc){
		super.xmlImport(PlainFileDoc);
		
			setContent(new String(PlainFileDoc.getChild("content").getValue()));
		
	}
	
	@Override
	public String checkType(){
		return "plainFile";
	}
	
	public Element xmlExport(){
		Element element = new Element("plain");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 
		element.addContent(new Element ("path").setText(getPath("")));
		element.addContent(new Element ("name").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner().toString()));
		element.addContent(new Element ("content").setText(getContent()));
		//FIXME element.addContent(new Element("perm").setText(getPermissions().toString()));
		
		//TODO Perm
		
		return element;
	}
}
