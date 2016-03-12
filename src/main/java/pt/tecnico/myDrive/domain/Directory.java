package pt.tecnico.myDrive.domain;

import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class Directory extends Directory_Base {
	
	
	public ArrayList<File> files = new ArrayList<File>();
	
	
    public Directory(User user, FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute, Directory father) {
        super();
        setUser(user);
    }

    public Directory(User user,FileSystem filesystem, Document xml){
    	xmlImport(xml);
    	setUser(user);
    	setFilesystem(filesystem);
    }

    public Directory getDir(String name){
    	File destiny = null;
    	destiny = getDirByName(name);
    	return (Directory) destiny;
    }
    
	public void addDir(Directory dir){
		files.add(dir);
		addFile(dir);
		setDimension(getDimension()+1);		
	}
	
	public File getDirByName(String username) {
        for (File dir : getFileSet()) {
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
		Document directoryDoc = super.xmlExport();
		return directoryDoc;
	}
}