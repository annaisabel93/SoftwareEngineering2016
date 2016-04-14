package pt.tecnico.mydrive.domain;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.mydrive.exception.RootCannotBeRemovedException;
import pt.tecnico.mydrive.exception.UsernameAlreadyExistsException;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();

    public byte[] array = {0,0,0,0};    
   
    public boolean validateToken(long token){
    	for(User user1 : getUserSet()){ //Verifies if the given token is already in use by another login
    		for(Login login : user1.getLoginSet()){
    			if(login.getToken() == token){
					return false;
    			}
    		}
    	}
    	return true;
    }     	
    
    public static FileSystem getInstance(){ //esta sempre a inicar um novo
    	FileSystem fs = FenixFramework.getDomainRoot().getFilesystem();
    	if(fs != null){
    		return fs;}
    	else{
    		log.trace("new FileSystem");
    		return new FileSystem();
    	}
    }
    
    private FileSystem() {
        setRoot(FenixFramework.getDomainRoot());
        setCounter(0);
        new Root(this, "SuperUser","root", "***", array, "root");       		
    }
    
    public User getUserByUsername(String username) {
        for (User user : getUserSet()) {
            if ((user.getUserName().equals(username))) {
                return user;
            }
        }
        return null;
    }
    
    public Entity getDirectoryHome(String username){ //devolve uma entity no meio de todas criadas no file sistem
    	for (User user : getUserSet()) {
    		if(user.getUserName().equals(username)){
    			return user.getHome(); //Pai do /home/root, que e /home
            	}
            }
        return null;
    } 
    
    public Directory getUserDir(String username){ //devolve o diretorio base do user
    	for (User user : getUserSet()) {
		if ( (user.getUserName().equals(username))){
    			return user.getHome();
	        }
        }
        return null;
    }
    
    public boolean hasUser(String username){
    	return getUserByUsername(username) != null;
    }
    
    @Override
    public void addUser(User user){
    	System.out.println("vai adicionar user" + user.getUserName());
    	if(hasUser(user.getUserName())){
    		System.out.println("entro na funcao add user e verico que o user existe");
    		throw new UsernameAlreadyExistsException(user.getUserName());}
    	else {
    		if((Directory)getRootDir()==null){
    			System.out.println("vai criar root dir\n\n\n\n\n\n");
    			Directory raiz =new Directory ("/",  user, Counter(),new DateTime());
    			setRootDir(raiz);
    			Directory home1 = new Directory (getRootDir(),"home",  user, Counter(),new DateTime());
    			raiz.addFile(home1);
    			
    			System.out.println(user == null);
    		}
    		Directory home = (Directory)getRootDir().getByName("home");
    		Directory userHome = new Directory(home,  user.getUserName(), user, getCounter(),new DateTime());
    		user.setHome(userHome);
    		super.addUser(user);
    	}
    }
  
    public int Counter(){
    	int x = getCounter();
    	setCounter(getCounter()+1);
    	return x;
    }
        
    public void cleanup() {
        try {
			for (User u: getUserSet())
			u.remove();
		} catch (RootCannotBeRemovedException e) {
			System.err.println("RootCannotBeRemovedException: " + e.getMessage());
		}
    }
    
  
    public void xmlImport(Document fsDoc){
    	Element rootElement = fsDoc.getRootElement();
    	System.out.println("ROOT_ELEMENT: " + fsDoc.getRootElement());
    	List<Element> listElement = rootElement.getChildren("user");
    	
    	int size = listElement.size();
    	
    	//user
    	for(int i=0; i<size;i++){
    		Element node = listElement.get(i);
    		String username = node.getAttribute("username").getValue();
    		String password = node.getChild("password").getValue();
    		String name = node.getChild("name").getValue();
    		String homeDir = node.getChild("home").getValue();
    		User user = getUserByUsername(username);
    		if(user == null){
    			user = new User(this, name, username, password, null, homeDir);
    		}   		
    		user.xmlImport(node);
    	}  	
    }
    
    public Document xmlExport(){
    	
    	Element element = new Element("myDrive");
		Document doc = new Document(element);
		
		for(User u: getUserSet()){
			element.addContent(u.xmlExport().detach());
				for(Entity e: u.getFileSet()){
					element.addContent(e.xmlExport().detach());
			}
		}
		return doc;
    }       
}
