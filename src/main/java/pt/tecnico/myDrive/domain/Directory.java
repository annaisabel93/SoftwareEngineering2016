package pt.tecnico.myDrive.domain;

import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class Directory extends Directory_Base {
	
	
	public ArrayList<Entity> files = new ArrayList<Entity>();
	
	
    public Directory(User user, FileSystem filesystem, String path, String filename, String owner, long id, int dimension, Directory father) {
        super();
        setUser(user);
    }

    public Directory(User user,FileSystem filesystem, Document xml){
    	xmlImport(xml);
    	setUser(user);
    	setFilesystem(filesystem);
    }

    public Directory getDir(String name){
    	Entity destiny = null;
    	destiny = getDirByName(name);
    	return (Directory) destiny;
    }
    
	public void addDir(Directory dir){
		files.add(dir);
		addEntity(dir);
		setDimension(getDimension()+1);		
	}
	
	public Entity getDirByName(String username) {
        for (Entity dir : getEntitySet()) {
            if (dir.getFilename().equals(username)) {
                return dir;
            }
        }
        return null;
    }
	
	
	
	public void xmlImport(Document directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Document xmlExport(){
    	Element element = new Element("Directory");
    	
    	Document directoryDoc = new Document(element);
    	directoryDoc = super.xmlExport();

		return directoryDoc;
	}
}