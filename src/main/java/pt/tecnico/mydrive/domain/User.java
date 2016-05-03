package pt.tecnico.mydrive.domain;


import java.util.ArrayList;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.InvalidUsernameException;


public class User extends User_Base {
	
	ArrayList<Directory> dirs = new ArrayList<Directory>();
    
    public User(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir) {
        super();
        setName(name);
        
        if(username.length() > 3)
        	setUserName(username);
        else
        	throw new InvalidUsernameException(username);
        
        setPassword(password);
        setMask(mask);
        setHomeDir(homeDir);
        setSystem(filesystem);
    }
    
    protected User(){  	
    }
    
    public User(FileSystem filesystem, Element xml){
    	super();
    	xmlImport(xml);
    	setSystem(filesystem);
    }
    
    @Override
    public void setSystem(FileSystem fs){
    	if(fs == null)
    		super.setSystem(null);
    	else{
    		fs.addUser(this);
    	}
    }
    
    public Login getLoginbyToken(long token){
    	for (Login login : getLoginSet()){
			if (login.getToken() == token)
				return login;			
		}
		return null;    
    }
       
    public void xmlImport(Element userEl){
        	setUserName(new String(userEl.getAttribute("username").getValue()));
        	setPassword(new String(userEl.getChild("password").getValue()));
        	setName(new String(userEl.getChild("name").getValue()));
        	setHomeDir(new String(userEl.getChild("home").getValue()));
        	//FIXME mask
        //setMask(userDoc.getRootElement().getChild("mask").getValue().getBytes());
        return;
        
    }

    public Element xmlExport(){
    	Element element = new Element("user");
    	element.setAttribute("username", getUserName()); 
    	element.addContent(new Element("password").setText(getPassword()));
    	element.addContent(new Element("name").setText(getName()));
    	element.addContent(new Element("home").setText(getHomeDir()));
    	//element.addContent("rwxd", getMask().toString());
//    	for(Directory d: getDirectorySet())
//			element.addContent(d.xmlExport().detach());
//    		
        return element; 
    }
    
    public Directory addDir(Directory dir){
    	dirs.add(dir);
    	return dir;
    }
    
    public Entity getEntityByName(String fileName){
    	for(Entity file: getFileSet())
    		if (file.getFilename().equals(fileName))
                return file;
        return null;
    }
    
    public void remove() { //FIXME --mexer na funcao ainda
	        setSystem(null);
	        setHome(null);
	}
}
