package pt.tecnico.myDrive.domain;


import java.util.ArrayList;
import org.jdom2.Document;

import java.io.UnsupportedEncodingException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class User extends User_Base {
	
	ArrayList<Directory> dirs = new ArrayList<Directory>();
    
    public User(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir) {
        super();
        setName(name);
        setUserName(username);
        setPassword(password);
        setMask(mask);
        setHomeDir(homeDir);
        setSystem(filesystem);
    }
    
    public User(FileSystem filesystem, Document xml){
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
    
    public void xmlImport(Document userDoc){
    	
        try{
        	setUserName(new String(userDoc.getRootElement().getAttribute("username").getValue().getBytes("UTF-8")));
        	setName(new String(userDoc.getRootElement().getChild("name").getValue().getBytes("UTF-8")));
        	setPassword(new String(userDoc.getRootElement().getChild("password").getValue().getBytes("UTF-8")));
        	setHomeDir(new String(userDoc.getRootElement().getChild("homeDir").getValue().getBytes("UTF-8")));
        }catch(UnsupportedEncodingException e) { System.err.println(e); }	
        setMask(userDoc.getRootElement().getChild("mask").getValue().getBytes());
        
    }

    public Document xmlExport(){
    	Element element = new Element("User");
    	element.setAttribute("username", getUserName()); 	
    	element.addContent(new Element("name").setText(getName()));
    	element.addContent(new Element("password").setText(getPassword()));
    	element.addContent(new Element("homeDir").setText(getHomeDir()));
    	//element.setAttribute("rwxd", getMask().toString());
    	
    	Document document = new Document(element);
    	
        return document; 
    }
    
    public Directory addDir(User user, FileSystem filesystem, String path, String filename, String owner, long id, DateTime lastModified, int dimension, Directory father){
    	Directory dir = new Directory(user,filesystem,path, filename,owner,id,dimension,father);
    	this.
    	dirs.add(dir);
    	return dir;
    }
    
    public void remove() { //FIXME --mexer na funcao ainda
		// TODO Auto-generated method stub
	        
	        setSystem(null);
	        deleteDomainObject();
	}
}
