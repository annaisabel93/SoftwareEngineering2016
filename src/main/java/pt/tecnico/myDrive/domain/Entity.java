package pt.tecnico.myDrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public abstract class Entity extends Entity_Base {
    
	//take filesystem off
	//add user in
	public Entity(FileSystem filesystem, Directory dir, String path, String filename,User user, long id, int dimension) {
        	super();
		this.init(filesystem,path,filename,user,id,dimension);
		setDirectory(dir);
	    }
	
	protected void init(FileSystem filesystem,String path, String filename, User user, long id, int dimension){
		setFilesystem(filesystem);
		setPath(path);
		setFilename(filename);
		setUser(user);
		setId(id);
		setDimension(dimension);
	}

	public Entity() {
		// TODO Auto-generated constructor stub
	}

	public abstract String getMyType();

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
