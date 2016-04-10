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
    
    @Override
    public void addUser(User user){
    	if(hasUser(user.getUserName())){
    		System.out.println("entro na funcao add user e verico que o user existe");
    		throw new UsernameAlreadyExistsException(user.getUserName());}
    	else{
    		if((Directory)getRootDir()==null){
    			System.out.println("User null3? "+ (this.logged_user == null));
    			Directory raiz =new Directory ("/",  user, Counter(),new DateTime());
    			setRootDir(raiz);
    			System.out.println("User null4? "+ (this.logged_user == null));
    			Directory home1 = new Directory (getRootDir(),"home",  user, Counter(),new DateTime());
    			raiz.addFile(home1);
    	 		
    		}
    		Directory home = (Directory)getRootDir().getByName("home");
    		System.out.println("User null5? "+ (this.logged_user == null));
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
        for (User u: getUserSet())
	    u.remove();
    }
    
  
    public void xmlImport(Document fsDoc){
    	Element rootElement = fsDoc.getRootElement();
    	System.out.println("ROOT_ELEMENT: " + fsDoc.getRootElement());
    	List<Element> listElement = rootElement.getChildren("User");
    	List<Element> listDir = rootElement.getChildren("Directory");
    	/*List<Element> listPlainFile = rootElement.getChildren("PlainFile");
    	List<Element> listApp = rootElement.getChildren("App");
    	List<Element> listLink = rootElement.getChildren("Link");*/
    	
    	int size = listElement.size();
    	int sizeDir = listDir.size();
    	/*int sizePlainFile = listPlainFile.size();
    	int sizeApp = listApp.size();
    	int sizeLink = listLink.size();*/
    	
    	for(int i=0; i<size;i++){
    		Element node = listElement.get(i);
    		String username = node.getAttribute("username").getValue();
    		String name = node.getChild("name").getValue();
    		String password = node.getChild("password").getValue();
    		String homeDir = node.getChild("homeDir").getValue();
    		User user = getUserByUsername(username);
    		if(user == null){
    			user = new User(this, name, username, password, null, homeDir);
    		}
    		//Element el = node.clone();
    		//Document userDoc = new Document(el.detach());
    		user.xmlImport(node);

    	}
    	for(int i=0; i<sizeDir ; i++){
    		Element node = listDir.get(i);
    		String filename = node.getChild("filename").getText();
    		//String owner = node.getChild("owner");
    		String path = node.getChild("path").getText();	
		//FIX TODO owner is now a user
		//LAST MODIFIED was added to dir
    		//Directory dir = (Directory)this.createFile(this, null,  filename,null,1000,2,null,0,null);
    		//dir.xmlImport(node);
    	}
    	/*
    	for(int i=0; i<sizeDir ; i++){
    		Element node = listPlainFile.get(i);
    		String filename = node.getChild("filename").getText();
    		String owner = node.getChild("owner").getText();
    		String path = node.getChild("path").getText();
    		
    		Directory dir = new Directory(getUserByUsername(owner), this, path, filename,owner,1000,2,null);
    		dir.xmlImport(node);
    	}
    	
    	for(int i=0; i<sizeDir ; i++){
    		Element node = listDir.get(i);
    		String filename = node.getChild("filename").getText();
    		String owner = node.getChild("owner").getText();
    		String path = node.getChild("path").getText();
    		
    		Directory dir = new Directory(getUserByUsername(owner), this, path, filename,owner,1000,2,null);
    		dir.xmlImport(node);
    	}
    	
    	for(int i=0; i<sizeDir ; i++){
    		Element node = listDir.get(i);
    		String filename = node.getChild("filename").getText();
    		String owner = node.getChild("owner").getText();
    		String path = node.getChild("path").getText();
    		
    		Directory dir = new Directory(getUserByUsername(owner), this, path, filename,owner,1000,2,null);
    		dir.xmlImport(node);
    	}*/
    	
    	
    }
    
    public Document xmlExport(){
    	
    	Element element = new Element("FileSystem");
		Document doc = new Document(element);
		
		for(User u: getUserSet()){
			element.addContent(u.xmlExport().detach());
				for(Entity e: u.getFileSet()){
					element.addContent(e.xmlExport().detach());
			}
		//System.out.println(u.xmlExport().detachRootElement());
		}
		/*
		for(Entity e: getEntitySet()){
			if(e instanceof Directory)
			element.addContent(e.xmlExport().getChild());
		}*/
		return doc;
    }   
    
}
