package pt.tecnico.myDrive.domain;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Directory extends Directory_Base {
	
	
    
    public Directory(User user, FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute) {
        super();
        setUser(user);
    }

    public Directory(User user,FileSystem filesystem, Document xml){
    	xmlImport(xml);
    	setUser(user);
    	setFilesystem(filesystem);
    }

	public void addDir(Directory dir){
		addFile(dir);
		
	}
	public void xmlImport(Document directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Document xmlExport(){
		Document directoryDoc = super.xmlExport();
		return directoryDoc;
	}
}