package pt.tecnico.myDrive.domain;


import java.util.ArrayList;
import org.jdom2.Document;

import java.io.UnsupportedEncodingException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class User extends User_Base {
	
	ArrayList<Directory> dirs = new ArrayList<Directory>();
    
    public User(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir, boolean isRoot) {
        super();
        setName(name);
        setUserName(username);
        setPassword(password);
        setMask(mask);
        setHomeDir(homeDir);
        setIsRoot(isRoot);
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
    	element.setAttribute("name", getName());
    	element.setAttribute("password", getPassword());
    	element.setAttribute("homeDir", getHomeDir());
    	element.setAttribute("rwxd", getMask().toString());
    	
    	Document document = new Document(element);
    	
        return document; 
    }
    
    public Directory addDir(User user, FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute){
    	Directory dir = new Directory(user,filesystem,filename,owner,id,lastModified,dimension,read,write,delete,execute);
    	dirs.add(dir);
    	return dir;
    }
}
