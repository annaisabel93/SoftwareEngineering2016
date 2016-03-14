package pt.tecnico.myDrive.domain;

import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class Directory extends Directory_Base {
	
	
	public ArrayList<Entity> files = new ArrayList<Entity>();
	public ArrayList<Directory> diretorias = new ArrayList<Directory>();
	public ArrayList<PlainFile> plains = new ArrayList<PlainFile>();
	public ArrayList<Link> links = new ArrayList<Link>();
	public ArrayList<App> apps = new ArrayList<App>();
	
    public Directory(User user, FileSystem filesystem, String path, String filename, String owner, long id, int dimension, Directory father) {
        super();
        setUser(user);
    }

    public Directory(User user,FileSystem filesystem, Element xml){
    	xmlImport(xml);
    	setUser(user);
    	setFilesystem(filesystem);
    }

    public Directory getDir(String name){
    	Entity destiny = null;
    	destiny = getDirByName(name,"directory");
    	return (Directory) destiny;
    }
    
	public void addDir(Directory dir){
		files.add(dir);
		addEntity(dir);
		setDimension(getDimension()+1);		
	}
	
	public Entity getDirByName(String dir_name, String type) {
        if(type.equals("directory")){
        	for (Directory dir : diretorias) {
        		if (dir.getFilename().equals(dir_name)) {
        			return dir;
        		}
        	}
        }
        if(type.equals("plainfile")){
        	for (PlainFile plain : plains) {
        		if (plain.getFilename().equals(dir_name)) {
        			return plain;
        		}
        	}
        }
        
        if(type.equals("link")){
        	for (Link link : links) {
        		if (link.getFilename().equals(dir_name)) {
        			return link;
        		}
        	}
        }
        
        if(type.equals("app")){
        	for (App app : apps) {
        		if (app.getFilename().equals(dir_name)) {
        			return app;
        		}
        	}
        }
        return null;
    }
	
	
	
	public void xmlImport(Element directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Element xmlExport(){
    	
    	
    	//metodo com heranca: not working
    	
//    	Document directoryDoc = super.xmlExport();
//    	Element element = new Element("Directory");
//    	directoryDoc.getRootElement().addContent(element); 
//		return directoryDoc;
		Element element = new Element("Directory");
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 	    	
		element.addContent(new Element ("filename").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner()));
		element.addContent(new Element("path").setText(getPath()));
		Document directoryDoc = new Document(element);
		return element;
	}
	
}