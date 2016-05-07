package pt.tecnico.mydrive.domain;


import java.util.ArrayList;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.InvalidPasswordException;
import pt.tecnico.mydrive.exception.ContentCannotBeNullException;
import pt.tecnico.mydrive.exception.DirectoryCannotHaveContentException;
import pt.tecnico.mydrive.exception.UnknownFileTypeException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.UsernameAlreadyExistsException;
import pt.tecnico.mydrive.exception.InexistentPointerForLinkException;
import pt.tecnico.mydrive.exception.InvalidUsernameException;


public class User extends User_Base {
	
	ArrayList<Directory> dirs = new ArrayList<Directory>();
    
	public User(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir) {
		super();
		if(username.length() <= 2){
			throw new InvalidUsernameException(username);
		}
		if(password.length() <= 7){
			throw new InvalidPasswordException(password);
		}
		setName(name);
		setUserName(username);
		setPassword(password);

        setMask(mask);
        setHomeDir(homeDir);
        setSystem(filesystem);
    }
    
    protected User(){}
    
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
 

   


    public void createFile(long token, String fileName, DateTime lastModified, String content, String type) 
		throws UnknownFileTypeException, DirectoryCannotHaveContentException, UserHasInvalidPermissionsException, InexistentPointerForLinkException {
		
		Directory userHome = this.getHome();
		userHome.checkCreate(this, fileName);
		
		switch(type) {
			case "Directory":
				new Directory(userHome, fileName, this, 3, lastModified);
				break;
			case "App":
				new App(userHome, fileName, this, 5, lastModified, content);
				break;
			case "PlainFile":
				new PlainFile(userHome, fileName, this, 2, lastModified, content);
				break;
			case "Link":
				this.getLoginbyToken(token).checkExistance(content);
				new Link(userHome, fileName, this, 1, lastModified, content);
				break;

			default:
				throw new UnknownFileTypeException(type);
		}
    }

	

      
    public void xmlImport(Element userEl){
        	setUserName(new String(userEl.getAttribute("username").getValue()));
            if(((CharSequence) userEl.getChild("password")).length() > 7)
            	setPassword(new String(userEl.getChild("password").getValue()));
            else
            	throw new InvalidPasswordException(userEl.getChild("password").toString());
        	
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
