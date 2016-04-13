package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import org.joda.time.DateTime;

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
	}

	public Entity() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract String checkType();
	
	public void checkDelete(Login login){
		checkPermissions(login, "delete");
		delete();
	}

	public void delete(){
		getOwner().removeFile(this);
		setParent(null);
		setOwner(null);
	}

	public String getPath(String path_until_now){ //tem que se passar   "/nome_do_ficheiro" 
		Directory parent = getParent();
		if(parent == null){
			return path_until_now;
		}
		else{
			return getPath(getFilename()+"/"+path_until_now);
		}
	}
	
	public boolean checkPermissions(Login login, String action){
		User loggedUser = login.getUser();	
		User owner = getOwner();
		
		if (loggedUser == owner) {
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
}
