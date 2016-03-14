package pt.tecnico.myDrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Entity extends Entity_Base {
    
	public Entity(FileSystem filesystem, Directory dir, String path, String filename, String owner, long id, int dimension) {
        super();
        setFilename(filename);
        setOwner(owner);
        setId(id);
        setDimension(dimension);
        setFilesystem(filesystem);
        setPath(path);
        setDirectory(dir);
    }
	

	public Entity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setFilesystem(FileSystem fs){
		if(fs == null)
			super.setFilesystem(null);
		else{
			fs.addEntity(this);
		}
	}
	
	public void xmlImport(Element filedoc){
			setFilename(new String(filedoc.getChild("filename").getValue()));
			setOwner(new String(filedoc.getChild("owner").getValue()));
			setPath(new String (filedoc.getChild("path").getValue()));
			return;
	}
	
	public Element xmlExport(){	
			Element element = new Element("Entity");
			return element;    
	}
    
}
