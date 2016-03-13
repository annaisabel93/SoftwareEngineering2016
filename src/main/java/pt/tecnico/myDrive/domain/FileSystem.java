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
import java.util.ArrayList;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

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
    
  // funcao para teste ler comandos de input e chamar funcoes a partir dai------------------------------------------------------------
    public void MainLoop(){
    	setCounter(0);
    	//addUser("root");
    	String input;
    	/*
    	0-Sair
    	1-Login
    	2-Adicionar utilizador
    	3-Adicionar Diretoria
    	4-Remover Diretoria
    	5-Ir para outra diretoria (usar cd <filename> ou cd .. ou cd .)
    	6-Criar Ficheiro de Texto
    	7-Remover Ficheiro de Texto
    	8-Imprimir o conteudo de um ficheiro na diretoria atual
    	9-Imprimir o conteudo da directoria 
    	 */
    	
    	
    	while(true){
    		if(logged_user != null){
    			System.out.println("Currently logged with:"+this.logged_user.getUserName());
    			System.out.println("Working dir:"+workingDir.getFilename());
    		}
    		System.out.println("Chose one:\n0-Sair\n1-Login\n2-Adicionar utilizador\n3-Adicionar Diretoria\n4-Remover Diretoria\n"
    				+ "5-Ir para outra Diretoria\n6-Criar Ficheiro de texto\n7-Remover ficheiro de texto\n8-Imprimir conteudo de um ficheiro na diretoria atual\n9-Imprimir o conteudo da directoria\n10-Logout");
    		
    		input =  System.console().readLine();
    		if(input.equals("0")){break;} //verificar se .next() , do scanner, tambem consome o \n ou nao
    		
    		if(input.equals("1")){ login(); continue; }
    		
    		if(input.equals("2")){//so estou a deixar adicionar um user se ninguem estiver logado
    			String username;
    			if(this.logged_user != null){
    				System.out.println("Logout first");
    				continue;
    			}
    			System.out.println("Username?");
    			username =  System.console().readLine();
    			adicionaUser(username);
    			continue;
    		}
    		
    		if(input.equals("3")){
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			AddDirtoCurrent( System.console().readLine());
    			continue;
    		}
    		
    		if(input.equals("4")){ //Nao implementado
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			//chama aqui a funcao para remover
    			//atencao que so pode remover diretorias dentro da dir atual
    			continue;
    		}
    		
    		
    		if(input.equals("5")){ // mover entre diretorias
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Ready to move");
    			moveDir( System.console().readLine());
    			continue;
    		}
    		
    		if(input.equals("10")){
    			this.logged_user = null;
    			this.workingDir  = null;
    			System.out.println("Logged out");
    			continue;
    		}
    		
    		if(input.equals("8")){
    			printReadMe();
    			continue;
    		}
    		
    		if(input.equals("9")){
    			printHome();
    			continue;
    		}
    		
    		if(input.equals("6")){
    			
    		}
    	}
    }
	
     //fim--------------------------------------------------------------------
    
    


	public void login(){
    	 String username;
    	 String pw;
    	 System.out.println("Username:");
    	 username =  System.console().readLine();
    	 User user = getUserByUsername(username);
    	 if (user == null){
    		 System.out.println("Username doesn't exist");
    		 return;
    	 }
    	 pw =  System.console().readLine();
    	 if (pw.equals(user.getPassword()) == false){
    		 System.out.println("Wrong pw");
    		 return;
    	 }
    	 this.logged_user = user;
    	 
    	 this.workingDir = getUserDir(user.getUserName());
     }
     
     
     public void adicionaUser(String username){
    	User user = null;
    	DateTime date = new DateTime();
    	
    	 for (User user1 : getUserSet()) {
	            if ((user1.getUserName().equals(username))) { 
	            	System.out.println("Username already exists!\n");
	            	return;
	            }
		 }
    	
    	if(check1 == 0){//caso root - apenas ira executar 1 vez
    		user = new User(this, "SuperUser","root", "***", array, username);
    		
 
    		Directory home =	new Directory(this, null, "/home", "home",  "root", getCounter(), 2);
    		System.out.println(home.getFilename());
    		files.add(home);
    		getEntitySet().add(home);
    		check1 = 1;
    	}
    	else{
    		
    		
    		user = new User(this, username, username, username, array, username);


    		setCounter(getCounter()+1);
    		
    		Directory home_dir = (Directory) getDirectoryHome("home");

    		System.out.println("ver se é null"+home_dir != null);

    		Directory dir = new Directory(this, home_dir, "/home/"+username, username, username, getCounter(), 2);
    		dir.setLastModified(date);
    		Directory Dir =	user.addDir(dir);
    		files.add(Dir);
    		getEntitySet().add(Dir);
    	}
     }
     
     private void printReadMe() {
    	 String homeDir = this.logged_user.getHomeDir();
    	 ArrayList<Entity> homeFiles = this.logged_user.getDirectory().getDir(homeDir).files;
    	 for (Entity entity : homeFiles) {
    		 if(entity.getFilename().equals("README") && entity.getClass().equals(PlainFile.class)) {
    			 PlainFile file = (PlainFile) entity;
    			 System.out.println(file.getContent());
    		 }	 
    	 }
    	 
     }
     
     private void printHome() {
    	 String homeDir = this.logged_user.getHomeDir();
    	 Directory home = this.logged_user.getDirectory().getDir(homeDir);
    	 for (Entity entity : home.files) {
    		 System.out.println(entity.getFilename());
    	 }
    	 
     }
     
     
     public void AddDirtoCurrent(String name){// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 setCounter(getCounter()+1);
    	 Directory dir = new Directory(this,workingDir,  workingDir.getFilename()+"\\"+name, name, this.logged_user.getUserName(), getCounter(), 2);
    	 this.workingDir.addDir(dir);
    	 dir.setLastModified(date);
    	 files.add(dir);
    	 System.out.println("File: "+name+" created");
     }
    	
    	
    public void moveDir(String directory_destiny){ //unfinished
    	Directory destiny = null;
    	destiny = this.workingDir.getDir("\\"+directory_destiny);
    	if(destiny == null){
    		System.out.println("Directory does not exist within this directory");
    		return;
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
        	//System.out.println("\n\n\n\n\n\n\n"+user.getUserName()+"------"+username+"------\n\n\n\n");
            if ((user.getUserName().equals(username))) {
            	//System.out.println("Vai retornar o user");
                return user;
            }
        }
        //System.out.println("Vai retornar null");
        return null;
    }
    
    public Entity getDirectoryHome(String dir_name){ //devolve uma entity no meio de todas criadas no file sistem
    	System.out.println("chega aqui11 -----"+getEntitySet().isEmpty()+" \n\n");
    	for (Entity entity : getEntitySet()) {
    		Entity dir = (Directory) entity;
    		System.out.println(dir.getFilename());
            if ((entity.getFilename().equals(dir_name))) {
            	System.out.println("chega aqui33 \n\n");
            	if(entity.getDirectory() == null){
                return entity;
            	}
            }System.out.println("chega aqui444 \n\n");
        }
        //System.out.println("Vai retornar null");
        return null;
    }
    
    public Entity getEntityfromDir(String name){//inacabado, deve devolver uma entidade dentro de uma diretoria predefinida apenas
    	return null;
    }
    
    public Directory getUserDir(String username){ //devolve o diretorio base do user
    	System.out.println(getEntitySet().isEmpty());
    	for (Entity entity : getEntitySet()) {
    		System.out.println(entity.getFilename());
            if ((entity.getFilename().equals(username))) {
            	System.out.println("vai comprar: "+entity.getDirectory().getPath()+" e "+ this.workingDir.getPath());
            	if((entity.getDirectory().getPath()).equals(this.workingDir.getPath())){
                return (Directory) entity;
            	}
            }
        }
        System.out.println("Vai retornar null");
        return null;
    }
    
    
    
    public boolean hasUser(String username){
    	//System.out.println("vai ver se "+username+" existe");
    	return getUserByUsername(username) != null;
    }
    
    @Override
    public void addUser(User user){
    	System.out.println("Vai adicionar "+user.getUserName());
    	/*if(hasUser(user.getUserName())){
    		System.out.println("entro na funcao add user e verico que o user existe");
    		throw new UsernameAlreadyExistsException(user.getUserName());}
    	else{
    	*/	
    		super.addUser(user);
    		System.out.println("eu adicionei o "+user.getUserName());
    }
    public void cleanup() {
        for (User u: getUserSet())
	    u.remove();
    }
    
    public void xmlImport(Document fsDoc){
    	List<Element> elList = fsDoc.getRootElement().getChildren();
    	
    	for(int i=0; i<elList.size(); i++){
    		Element user = elList.get(i);
    		//System.out.println("Lista: " + elList.get(i));
    		String username = user.getAttribute("username").getValue();
    		String name = user.getChild("name").getValue();
    		String password = user.getChild("password").getValue();
    		String homeDir = user.getChild("homeDir").getValue();
    	
    	
    	//vai dar asneira
    	
//    	User newUser = getUserByUsername(username);
//    	if (newUser == null)
//    		newUser = new User(this, name, username, password, null, homeDir);
//    	Element el = user.detach();
//    	Document doc = new Document(el);
//    	newUser.xmlImport(doc);
    	}
    }
    
    public Document xmlExport(){
    	Element element = new Element("FileSystem");
		Document doc = new Document(element);
		for(User u: getUserSet())
			element.addContent(u.xmlExport().detachRootElement());
		return doc;
    }   
    
}