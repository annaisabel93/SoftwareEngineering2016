package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import java.util.Scanner;
import java.util.Set;

import javax.print.Doc;

import java.util.HashSet;
import java.util.List;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.io.BufferedReader;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.*;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    public Directory workingDir  = null;
    public User logged_user  = null;
    public byte[] array = {0,0,0,0};
    public int check1 = 0;

    Set<Entity> files = new HashSet<Entity>();
    
	
    /*
    public FileSystem(){
    	super();
    	setCounter(0);

    	addUser("root");
    }
    */
    
    public void setWorkingDir(Directory dir){
    	this.workingDir = dir;
    }
    
    public Directory getWorkDir(){
    	return this.workingDir;
    }



	public void login(String username) throws UsernameDoesntExistException, WrongPasswordException{
    	 String pw;
    	 //username =  System.console().readLine();
    	 User user = getUserByUsername(username);
    	 if (user == null){
    		 throw new UsernameDoesntExistException(username);
    	 }
    	 pw = username;
    	 //System.out.println("Password dada: " + pw);
    	 //System.out.println("Password do utilizador: " + user.getPassword());
    	 //pw =  System.console().readLine();
    	 if (pw.equals(user.getPassword()) == false){
    		 throw new WrongPasswordException();
    	 }
    	 this.logged_user = user;
    	 
    	 this.workingDir = getUserDir(user.getUserName());

     }
     
     
     public void adicionaUser(String username) throws UsernameAlreadyExistsException{
    	User user = null;
    	DateTime date = new DateTime();
    	
    	 for (User user1 : getUserSet()) {
	            if ((user1.getUserName().equals(username))) { 
	            	throw new UsernameAlreadyExistsException(username);
	            }
		 }
    	
    	 if(check1 == 0){//caso root - apenas ira executar 1 vez
     		setCounter(0);
     		user = new User(this, "SuperUser","root", "***", array, username);
     		
     		Directory raiz =	new Directory(this, null, "/", "/",  "root", getCounter(), 2);
     		files.add(raiz);
     		getEntitySet().add(raiz);
     		setCounter(getCounter()+1);
     		Directory home =	new Directory(this, raiz, "/home", "home",  "root", getCounter(), 2);
     		files.add(home);
     		getEntitySet().add(home);
     		raiz.addDir(home);
     		setCounter(getCounter()+1);
     		Directory home_root =	new Directory(this, home, "/home/root", "root",  "root", getCounter(), 2);
     		files.add(home_root);
     		home.addDir(home_root);
     		getEntitySet().add(home_root);
     		check1 = 1;
     	}
    	else{
    		
    		
    		user = new User(this, username, username, username, array, username);


    		setCounter(getCounter()+1);
    		
    		Directory home_dir = (Directory) getDirectoryHome("home");


    		Directory dir = new Directory(this, home_dir, "/home/"+username, username, username, getCounter(), 2);
    		dir.setLastModified(date);
    		Directory Dir =	user.addDir(dir);
    		files.add(dir);
    		home_dir.addDir(dir);
    		getEntitySet().add(dir);
    	}
     }
     
     public void printReadMe(String name) {
    	 for (PlainFile plain : this.workingDir.plains) {
    		 if(plain.getFilename().equals(name) ) {
    			 System.out.println(plain.getContent());
    		 }	 
    	 }
    	 
     }
          
     public void WriteOnFile(String name , String content){
    	 for (PlainFile plain : this.workingDir.plains) {
    		 if(plain.getFilename().equals(name)){
    			 plain.addContent(content);
    			 return;
    		 }
    	 }
    	 System.out.println("Text file does not exist");
     }
     
     public void printHome() {
    	 System.out.println(".\n..");
    	 for (Directory dir : this.workingDir.diretorias) {
    		 System.out.println(dir.getFilename());
    	 }
    	 for (PlainFile plain : this.workingDir.plains) {
    		 System.out.println(plain.getFilename());
    	 }
    	 for (Link link : this.workingDir.links) {
    		 System.out.println(link.getFilename());
    	 }
    	 for (App app : this.workingDir.apps) {
    		 System.out.println(app.getFilename());
    	 }

    	 //parte do zé aqui---------------Print complexo----
    	 //System.out.println("Working directory: " + workingDir.getPath());
    	 //this.workingDir.printDir();    	 
     }
     
     
     public void RemoveDir(String dir_name){
    	 for (Directory dir : this.workingDir.diretorias) {
    		 if(dir.getFilename().equals(dir_name)){
    			 this.workingDir.DeleteEntity(dir_name, "Directory");
    			 return;
    		 }
    	 }
    	 System.out.println("Diretoria nao existe dentro da diretoria de trabalho!");

     }

     public void RemoveFile(String file_name){
    	 for (PlainFile plain : this.workingDir.plains) {
    		 if(plain.getFilename().equals(file_name)){
    			 this.workingDir.DeleteEntity(file_name, "Plain_File");
    			 return;
    		 }
    	 }	 
       	 System.out.println("Plain File nao existe dentro da diretoria de trabalho!");
        }
     
     
     public void AddDirtoCurrent(String name) throws DirectoryAlreadyExistsInsideWorkingDirException{// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 for (Directory dir : this.workingDir.diretorias) {
    		 if(dir.getFilename().equals(name)){
    			 throw new DirectoryAlreadyExistsInsideWorkingDirException();
    		 }
    	 }
    	 setCounter(getCounter()+1);
    	 if (this.workingDir.getFilename().equals("/")){
    		Directory dir = new Directory(this,this.workingDir,  workingDir.getPath()+name, name, this.logged_user.getUserName(), getCounter(), 2);
    		this.workingDir.addDir(dir);
    		dir.setLastModified(date);
       	 	files.add(dir);
    	 }
    	 else{
    		Directory dir = new Directory(this,this.workingDir,  workingDir.getPath()+"/"+name, name, this.logged_user.getUserName(), getCounter(), 2);
     		this.workingDir.addDir(dir);
     		dir.setLastModified(date);
        	files.add(dir);
    	 }
     }
     
     
     public void CreateTextFile(String file_name){
    	 DateTime date = new DateTime();
    	 for (PlainFile plain : this.workingDir.plains) {
    		 if(plain.getFilename().equals(file_name)){
    			 System.out.println("PlainFile ja existe na diretoria atual");
    			 return;
    		 }
    	 }
    	 setCounter(getCounter()+1);
    	 PlainFile textfile = new PlainFile(this,file_name,this.logged_user.getUserName(), getCounter(), date, 2, "");
    	 this.workingDir.addPlainFile(textfile);
    	 textfile.setLastModified(date);
    	 //getEntitySet().add(textfile);
    	 files.add(textfile);
     }
    	
    	
    public void moveDir(String directory_destiny) throws DirectoryDoesNotExistInsideWorkingDirException{ //unfinished
    	if(directory_destiny.equals(".")){//ficar na propria diretoria
    		return;
    	}
    	if(directory_destiny.equals("..")){ //voltar atras
    		if(this.workingDir.getPath().equals("/")){
    			return;
    		}
    		this.workingDir = this.workingDir.getDirectory();
    		return;
    	}
    	Directory destiny = null;
    	destiny = this.workingDir.getDir(directory_destiny);
    	if(destiny == null){
    		throw new DirectoryDoesNotExistInsideWorkingDirException(directory_destiny);
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
        
    }
    
    public User getUserByUsername(String username) {
        for (User user : getUserSet()) {
            if ((user.getUserName().equals(username))) {
                return user;
            }
        }
        return null;
    }
    
    public Entity getDirectoryHome(String dir_name){ //devolve uma entity no meio de todas criadas no file sistem
    	for (Entity entity : getEntitySet()) {
    		Entity dir = (Directory) entity;
            if ((entity.getFilename().equals(dir_name))) {
            	if(entity.getDirectory().getFilename().equals("/")){
                return entity;
            	}
            }
        }
        return null;
    }
    
    public Entity getEntityfromDir(String name){//inacabado, deve devolver uma entidade dentro de uma diretoria predefinida apenas
    	return null;
    }
    
    public Directory getUserDir(String username){ //devolve o diretorio base do user
    	String test = "/home/"+username;
    	for (Entity entity : getEntitySet()) {
    		if(entity.getPath().equals(test)){
    		return (Directory) entity;
    		}
        }
        return null;
    }
    
    
    
    public boolean hasUser(String username){
    	//System.out.println("vai ver se "+username+" existe");
    	return getUserByUsername(username) != null;
    }
    
    @Override
    public void addUser(User user){
    	/*if(hasUser(user.getUserName())){
    		System.out.println("entro na funcao add user e verico que o user existe");
    		throw new UsernameAlreadyExistsException(user.getUserName());}
    	else{
    	*/	
    		super.addUser(user);
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
    		String owner = node.getChild("owner").getText();
    		String path = node.getChild("path").getText();
    		
    		Directory dir = new Directory(this, null, path, filename,owner,1000,2);
    		dir.xmlImport(node);
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
			for(Entity e: getEntitySet()){
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