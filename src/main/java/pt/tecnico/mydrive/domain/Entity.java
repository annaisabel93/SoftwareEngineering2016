package pt.tecnico.mydrive.domain;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.InvalidPathLenghtException;

public abstract class Entity extends Entity_Base {
    
	//take filesystem off
	//add user in
	public Entity(Directory parent, String filename,User owner, long id,DateTime lastModified) {//potr o FS em vez do id, tirar o midify e por dentro
        	super();
        	setFilename(filename);
    		setOwner(owner);
    		setId(id);
    		setLastModified(lastModified);
    		setParent(parent);
        
        	init(parent,filename,owner,id,lastModified);
	    }
	
	protected void init(Directory parent, String filename, User owner, long id,DateTime lastModified){
		setFilename(filename);
		setOwner(owner);
		setId(id);
		setLastModified(lastModified);
		setParent(parent);
		setPermissions(owner.getMask());
	}

	public Entity() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract String checkType();
	
	public void checkDelete(User user){
		checkPermissions(user, "delete");
		delete();
	}
	
	public abstract int getSize();

	public void delete(){
		getOwner().removeFile(this);
		setParent(null);
		setOwner(null);
	}

	public String getPath(String path_until_now){ //tem que se passar   "/nome_do_ficheiro" 
		Directory parent = getParent();
		String parents = parent.toString();
		if(parents.getBytes().length < 1024){
			if(parent.getParent() == parent){
				return path_until_now;
			}
			else{
				return parent.getPath("/"+parent.getFilename()+path_until_now);
			}
		}
		else
			throw new InvalidPathLenghtException(parents.getBytes().length);
	}
	public abstract String read(User user);
	
	public boolean checkPermissions(User loggedUser, String action){
		
		User owner = getOwner();
		
		if (loggedUser.equals(owner)) {
			return true;
		}
		
		else
			if (action.equals("read"))
				if (getPermissions()[0] == 1)
					return true;
		
			if (action.equals("write"))
				if (getPermissions()[1] == 1)
					return true;
			
			if (action.equals("execute"))
				if(getPermissions()[2] == 1)
					return true;

			if (action.equals("delete"))
				if (getPermissions()[3] == 1)
					return true;
			
		return false;	
	}
	
	public String permtoString(){
		String perm = "";
		byte [] mask = new byte [4];
		mask = this.getPermissions();
		System.out.println(getPermissions() == null);
		if(mask[0] == 1)
			perm += "r";
		else
			perm += "-";
		
		if(mask[1] == 1)
			perm += "w";
		else
			perm += "-";
		
		if(mask[2] == 1)
			perm += "x";
		else
			perm += "-";
		
		if(mask[3] == 1)
			perm += "d";
		else
			perm += "-";
		
		
		return perm;
		
	}


	public void xmlImport(Element filedoc){
			setFilename(new String(filedoc.getChild("name").getValue()));
			//setOwner(new String(filedoc.getChild("owner").getValue()));    agora e um objeto user
			//setPath(new String (filedoc.getChild("path").getValue()));
			return;
	}
	
	public Element xmlExport(){	
			Element element = new Element("entity");
			return element;    
	}
	
	public abstract String execute();   
}
