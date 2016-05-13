package pt.tecnico.mydrive.domain;

import java.lang.reflect.InvocationTargetException;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.WrongFileTypeException;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile(Directory parent,String filename, User user, long id, DateTime lastModified,  String content) {
        super();
        init(parent,filename,user,id,lastModified);
		setContent(content);
        
    }
    

	public PlainFile() {
		// TODO Auto-generated constructor stub
	}
	
//    public String execute(){
//    	return getContent();
//    }
	
	
	public PlainFile(User owner, Element xml){
		xmlImport(xml);
		setOwner(owner);
	}
	public void addContent(User user, String content){
		if (checkPermissions(user, "write") == false)	
			throw new UserHasInvalidPermissionsException();
		setContent(content);
		setLastModified(new DateTime());
		getParent().setLastModified(new DateTime());
	}
	
	
	public int getSize(){
		return getContent().length();
	}
	/*
	public String read(Login login) throws UserHasInvalidPermissionsException{
		if(!(checkPermissions(login, "read")))
			throw new UserHasInvalidPermissionsException();
		else{
			return getContent();
		}
	}*/
	/*
	public PlainFile getFileByName(String filename) throws WrongFileTypeException{
		Entity file = getParent().getByName(filename);
		if(file instanceof PlainFile)
			return (PlainFile) file;
		else
			throw new WrongFileTypeException();
		
	}*/
	
	public String read(User user) throws UserHasInvalidPermissionsException, WrongFileTypeException, EntityDoesNotExistException{
			
		if(checkPermissions(user, "read") == false)
			throw new UserHasInvalidPermissionsException();
		return this.getContent();
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
		return "PlainFile";
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
		
		if (getOwner().getUserName().equals("root")){	//FIXME
			element.addContent(new Element("perm").setText("----")); //dirty hack, can't have mask working
		}
//		else
//			element.addContent(new Element("perm").setText(getPermissions().toString()));
		
		return element;
	}

	@Override
	public String execute(String name, String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return getContent();
	}
}
