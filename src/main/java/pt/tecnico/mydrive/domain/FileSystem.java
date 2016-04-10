package pt.tecnico.mydrive.domain;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.mydrive.exception.DirectoryAlreadyExistsInsideWorkingDirException;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;
import pt.tecnico.mydrive.exception.UsernameAlreadyExistsException;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.exception.WrongPasswordException;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    public Directory workingDir  = null;
    public User logged_user  = null;
    public byte[] array = {0,0,0,0};    
    
    public void setWorkingDir(Directory dir){
    	this.workingDir = dir;
    }
    
    public Directory getWorkDir(){
    	return this.workingDir;
    }

	public void login(String username) throws UsernameDoesntExistException, WrongPasswordException{
    	 String pw;
    	 User user = getUserByUsername(username);
    	 if (user == null){
    		 throw new UsernameDoesntExistException(username);
    	 }
    	 pw = username;
    	 if (pw.equals(user.getPassword()) == false){
    		 throw new WrongPasswordException();
    	 }
    	 this.logged_user = user;
    	 System.out.println(this.logged_user.getName());
    	 
    	 this.workingDir = (Directory) getDirectoryHome(username);

     }

       
     
     public void printReadMe(String name)throws TexFileDoesNotExistException {
    	 for (Entity plain : this.workingDir.getFileSet()) {
    		 if(plain.getFilename().equals(name) && plain instanceof PlainFile ) {
    			 System.out.println(((PlainFile) plain).getContent());
    		 }	 
    	 }
    	 
     }
          
     public void WriteOnFile(String name , String content){
    	 try{
    		 this.workingDir.WriteToFile(content, name);
    	 }
    	 catch (TexFileDoesNotExistException e) {
    		    System.err.println("TextFileDoesNotExistException: " + e.getMessage());
    	 }
     }
     
     public void printHome() {
    	 System.out.println(".\n..");
    	 for (Entity dir : this.workingDir.getFileSet()) {
    		 System.out.println(dir.getFilename());
    	 }

    	 //---------------Print complexo----
    	 //System.out.println("Working directory: " + workingDir.getPath());
    	 //this.workingDir.printDir();    	 
     }
        
     public void RemoveEntity(String dir_name){
    	 for (Entity dir : this.workingDir.getFileSet()) {
    		 if(dir.getFilename().equals(dir_name)){
    			 dir.delete();
    			 return;
    		 }
    	 }
    	 System.out.println(dir_name + "File nao existe dentro da diretoria de trabalho!" + this.workingDir.getFilename());
     }
    
     public void AddDirtoCurrent(String name) throws DirectoryAlreadyExistsInsideWorkingDirException{// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 for (Entity dir : this.workingDir.getFileSet()) {
    		 if(dir.getFilename().equals(name)){
    			 throw new DirectoryAlreadyExistsInsideWorkingDirException();
    		 }
    	 }
    	 setCounter(getCounter()+1);
    	 if (this.workingDir.getFilename().equals("/")){
    		System.out.println("User null2? "+ (this.logged_user == null));
    		Directory dir = new Directory(this.workingDir, name, this.logged_user, getCounter(), date);
    		this.workingDir.addFile(dir);
    		this.logged_user.addFile(dir);
    	 }
    	 else{
    		System.out.println("User null1? "+ (this.logged_user == null));
    		Directory dir = new Directory(this.workingDir,  name, this.logged_user, getCounter(), date);
     		this.workingDir.addFile(dir);
     		this.logged_user.addFile(dir);
    	 }
     }
     
     public void CreateTextFile(String file_name){
    	 DateTime date = new DateTime();
    	 for (Entity plain : this.workingDir.getFileSet()) {
    		 if(plain.getFilename().equals(file_name)){
    			 System.out.println("PlainFile ja existe na diretoria atual");
    			 return;
    		 }
    	 }
    	 new PlainFile(this.workingDir, file_name, this.logged_user, Counter(), date,  "");
     }
    		
    public void moveDir(String directory_destiny) throws DirectoryDoesNotExistWithinDirectoryException{ //unfinished
    	if(directory_destiny.equals(".")){//ficar na propria diretoria
    		return;
    	}
    	if(directory_destiny.equals("..")){ //voltar atras
    		if(this.workingDir.getParent()==null){
    			return;
    		}
    		this.workingDir = this.workingDir.getParent();
    		return;
    	}
    	Directory destiny = null;
    	
    	destiny = this.workingDir.getDir(directory_destiny);
    	if(destiny == null){
    		throw new DirectoryDoesNotExistWithinDirectoryException(directory_destiny);
    	}
    	this.workingDir = destiny;
    	
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
    
    
    public int Counter(){
    	int x = getCounter();
    	setCounter(getCounter()+1);
    	return x;
    }
    
    
    public void cleanup() {
        for (User u: getUserSet())
	    u.remove();
    }
    
  
    public void xmlImport(Document fsDoc){
    	Element rootElement = fsDoc.getRootElement();
    	System.out.println("ROOT_ELEMENT: " + fsDoc.getRootElement());
    	List<Element> listElement = rootElement.getChildren("user");
    	List<Element> listDir = rootElement.getChildren("directory");
    	List<Element> listPlain = rootElement.getChildren("plain");
    	List<Element> listApp = rootElement.getChildren("app");
    	List<Element> listLink = rootElement.getChildren("link");
    	
    	int size = listElement.size();
    	int sizeDir = listDir.size();
    	int sizePlain = listPlain.size();
    	int sizeApp = listApp.size();
    	int sizeLink = listLink.size();
    	
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
    	//dir
//    	for(int i=0; i<sizeDir ; i++){
//    		Element node = listDir.get(i);
//    		String path = node.getChild("path").getText();	
//    		String name = node.getChild("name").getText();
//    		String owner = node.getChild("owner").getText();
//    		//TODO perm
//    	}
//    	
//    	for(int i=0; i<sizePlain ; i++){
//    		Element node = listPlain.get(i);
//    		String path = node.getChild("path").getText();	
//    		String name = node.getChild("name").getText();
//    		String owner = node.getChild("owner").getText();
//    		//TODO perm
//    		String content = node.getChild("contents").getText();
//    		
//    		
//    	}
//    	
//    	for(int i=0; i<sizeApp ; i++){
//    		Element node = listApp.get(i);
//    		String path = node.getChild("path").getText();	
//    		String name = node.getChild("name").getText();
//    		String owner = node.getChild("owner").getText();
//    		//TODO perm
//    		String method = node.getChild("method").getText();
//    	}
//    	
//    	for(int i=0; i<sizeLink ; i++){
//    		Element node = listLink.get(i);
//    		String path = node.getChild("path").getText();	
//    		String name = node.getChild("name").getText();
//    		String owner = node.getChild("owner").getText();
//    		//TODO perm
//    		String value = node.getChild("value").getText();
//    	}
    	
    	
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
