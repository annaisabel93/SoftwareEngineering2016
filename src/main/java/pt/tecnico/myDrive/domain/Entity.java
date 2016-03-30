package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public abstract class Entity extends Entity_Base {
    
	//take filesystem off
	//add user in
	public Entity(FileSystem filesystem, Directory parent, String filename,User user, long id,DateTime lastModified) {
        	super();
		this.init(filesystem, parent,filename,user,id,lastModified);
	    }
	
	protected void init(FileSystem filesystem, Directory parent, String filename, User owner, long id,DateTime lastModified){
		setOwner(owner);
		setId(id);
		setLastModified(lastModified);
		setParent(parent);
	}

	public Entity() {
		// TODO Auto-generated constructor stub
	}

	public abstract String getMyType();

	
	public String getPath(String path_until_now){ //tem que se passar   "/nome_do_ficheiro" 
		Directory parent = getParent();
		if(parent == null){
			return path_until_now;
		}
		else{
			return getPath(getFilename()+"/"+path_until_now);
		}
	}
	
	
	@Override
	/*
	public void setFilesystem(FileSystem fs){
		if(fs == null)
			super.setFilesystem(null);
		else{
			fs.addEntity(this);
		}
	}
	*/
	
	public void setOwner(User owner) {};
	
	public void xmlImport(Element filedoc){
			setFilename(new String(filedoc.getChild("filename").getValue()));
			//setOwner(new String(filedoc.getChild("owner").getValue()));    agora e um objeto user
			//setPath(new String (filedoc.getChild("path").getValue()));
			return;
	}
	
	public Element xmlExport(){	
			Element element = new Element("Entity");
			return element;    
	}
    
}
