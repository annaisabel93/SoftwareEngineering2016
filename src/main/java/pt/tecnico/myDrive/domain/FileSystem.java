package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    public Directory workingDir  = null;
    public User logged_user  = null;
    public byte[] array = {0,0,0,0};

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
    	addUser("root");
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
    	8-Imprimir o conteudo do ficheiro home/README
    	9-Imprimir o conteudo da directoria /home 
    	 */
    	
    	
    	while(true){
    		if(logged_user != null){
    			System.out.println("Currently loged with:"+this.logged_user.getUserName());
    			System.out.println("Working dir:"+this.workingDir.getFilename());
    		}
    		else{ System.out.println("Chose one:\n0-Sair\n1-Login\n2-Adicionar utilizador\n3-Adicionar Diretoria\n4-Remover Diretoria\n"
    				+ "5-Ir para outra Diretoria\n6-Logout\n");
    		
    		}
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
    			addUser(username);
    			System.out.println("Created user "+username);
    			continue;
    		}
    		
    		if(input.equals("3")){
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			prepareDir( System.console().readLine());
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
    		
    		if(input.equals("6")){
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
    	}
    	
    }
	
     //fim--------------------------------------------------------------------
    
    


	public void login(){
    	 String username;
    	 String pw;
    	 System.out.println("Username:");
    	 username =  System.console().readLine();
    	 User user = getUserByUsername("root");
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
    	 this.workingDir = user.getDirectory();
     }
     
     
     public void addUser(String username){
    	User user = null;
    	DateTime date = new DateTime();
    	if(this.logged_user == null){//caso root - apenas ira executar 1 vez
    		user = new User(this, "root", "Super User", "***", array, username);
    		addUser(user);
    		//adicionar home directory e a diretoria raiz
    		setCounter(getCounter()+1);
    		//precisa de \\ para reconhecer \ dentro da string
    		Directory home =	new Directory(user,this, "\\home", "home",  "root", getCounter(), 2, null);
    		addEntity(home);
    		files.add(home);
    	}
    	else{
    		user = new User(this, username, username, username, array, username);
    		addUser(user);
    		setCounter(getCounter()+1);
    		Directory Dir =	user.addDir(user,this,username, this.workingDir.getFilename()+"\\"+username, username, (long)getCounter(), date, 2, this.workingDir );
    		files.add(Dir);
    		addEntity(Dir);
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
     
     
     public void prepareDir(String name){// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 setCounter(getCounter()+1);
    	 Directory Dir = this.logged_user.addDir(this.logged_user,this,name,  workingDir.getFilename()+"\\"+name, this.logged_user.getUserName(), getCounter(), date, 2, this.workingDir);
    	 files.add(Dir);
    	 addEntity(Dir);
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
    		System.out.println("\n\n\n\nefefefefefefe\n\n\n\n\n\n\n");
    		return fs;}
    	else{
    		log.trace("new FileSystem");
    		System.out.println("\n\n\n\nererererererererereree\n\n\n\n\n\n\n");
    		return new FileSystem();
    	}
    }
    
    private FileSystem() {
        setRoot(FenixFramework.getDomainRoot());
        
    }
    
    public User getUserByUsername(String username) {
        for (User user : getUserSet()) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean hasUser(String username){
    	return getUserByUsername(username) != null;
    }
    
    @Override
    public void addUser(User user) throws UsernameAlreadyExistsException{
    	if(hasUser(user.getUserName()))
    		throw new UsernameAlreadyExistsException(user.getUserName());
    	
    	super.addUser(user);
    }
    
    public void cleanup() {
        for (User u: getUserSet())
	    u.remove();
    }
    
    public void xmlImport(Document fsDoc){
    	for(Element node: fsDoc.getRootElement().getChildren("User")){
			String username = node.getAttribute("username").getValue();
			User user = getUserByUsername(username);
			
			if(user == null)
				user = new User(this, username, username, username,null, "home"); //FIXME --argumentos
			Document fsdoc = new Document(node);
			List content = fsdoc.getRootElement().cloneContent(); 
			user.xmlImport(fsdoc);
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