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
    
    public void xmlImport(Element userEl){
        	setUserName(new String(userEl.getAttribute("username").getValue()));
        	setName(new String(userEl.getChild("name").getValue()));
        	setPassword(new String(userEl.getChild("password").getValue()));
        	setHomeDir(new String(userEl.getChild("homeDir").getValue()));
        //setMask(userDoc.getRootElement().getChild("mask").getValue().getBytes());
        return;
        
    }

    public Document xmlExport(){
    	Element element = new Element("User");
    	element.setAttribute("username", getUserName()); 	
    	element.addContent(new Element("name").setText(getName()));
    	element.addContent(new Element("password").setText(getPassword()));
    	element.addContent(new Element("homeDir").setText(getHomeDir()));
    	//element.setAttribute("rwxd", getMask().toString());
//    	for(Directory d: getDirectorySet())
//			element.addContent(d.xmlExport().detach());
//    	
    	Document document = new Document(element);
    	
        return document; 
    }
    
    public Directory addDir(Directory dir){
    	dirs.add(dir);
    	return dir;
    }
    
    public void remove() { //FIXME --mexer na funcao ainda
		// TODO Auto-generated method stub
	        
	        setSystem(null);
	        deleteDomainObject();
	}
}
