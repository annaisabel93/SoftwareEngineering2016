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
	

	
	public Entity getDirByName(String dir_name) {
        for (Entity dir : getEntitySet()) {
            if (dir.getFilename().equals(dir_name)) {
                return dir;
            }
        }
        return null;
    }
	
	
	
	public void xmlImport(Document directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Document xmlExport(){
		Document directoryDoc = super.xmlExport();
		return directoryDoc;
	}
}