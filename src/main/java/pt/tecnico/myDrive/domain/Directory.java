package pt.tecnico.myDrive.domain;

import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class Directory extends Directory_Base {
	
	
	public ArrayList<Entity> files = new ArrayList<Entity>();
	
	
    public Directory(FileSystem filesystem, Directory dir, String path, String filename, String owner, long id, int dimension) {
        super();
        setFilename(filename);
        setDimension(dimension);
        setDirectory(dir);
        setFilesystem(filesystem);
        setPath(path);
        setOwner(owner);
        setId(id);
        

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
	
	public void printDir() {
		for(Entity entity: this.files) {
			String type = "unknown";
			//String[] parts = entity.getClass().toString().split("\\."); //detect type FIX?
			//type = parts[4];
			if(entity instanceof Directory) { type = "Directory"; }
			else if(entity instanceof App){ type = "App"; }
			else if(entity instanceof Link){ type = "Link"; }
			else if(entity instanceof PlainFile){ type = "PlainFile"; }
			System.out.println(type + " " + "permissions" + " " + entity.getDimension() + " " + entity.getOwner() + " " + entity.getId() + " " + entity.getLastModified() + " " + entity.getFilename()); //FIXME: add permissions
		}
	}
	
	public void xmlImport(Document directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Document xmlExport(){
		Document directoryDoc = super.xmlExport();
		return directoryDoc;
	}
}