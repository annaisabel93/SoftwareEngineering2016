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
import java.util.ArrayList;
import java.io.BufferedReader;

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
    
    public String Input() {
    	String input = "1";
    	
    	InputStreamReader sr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(sr);
		try {
			input = br.readLine();
		} catch(IOException e) { System.out.println("BufferedReader: " + e); }
    	
    	return input;
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
    			System.out.println("\nCurrently logged with:"+this.logged_user.getUserName());
    			System.out.println("Working dir:"+workingDir.getPath());
    		}
    		System.out.println("\n0-Sair\n1-Login\n2-Adicionar utilizador\n3-Adicionar Diretoria\n4-Remover Diretoria\n"
    				+ "5-Ir para outra Diretoria\n6-Criar Ficheiro de texto\n7-Remover ficheiro de texto\n8-Imprimir conteudo de um ficheiro na diretoria atual\n9-Imprimir o conteudo da directoria\n10-Logout\n");
    		
    	/*
    		InputStreamReader sr =new InputStreamReader(System.in);
    		BufferedReader br = new BufferedReader(sr);
    		try {
    			input = br.readLine();
    		} catch(IOException e) { System.out.println("BufferedReader: " + e); }
    		
    		Console c = System.console();
    		while( (input = c.readLine()) == null) {
    			
    		}*/
    		input = Input();
    		
    		
    		if(input.equals("0")){break;} //verificar se .next() , do scanner, tambem consome o \n ou nao
    		
    		if(input.equals("1")){ login(); continue; }
    		
    		if(input.equals("2")){//so estou a deixar adicionar um user se ninguem estiver logado
    			String username;
    			if(this.logged_user != null){
    				System.out.println("Logout first");
    				continue;
    			}
    			System.out.println("Username?");
    			username = Input();
    			//username =  System.console().readLine();
    			adicionaUser(username);
    			continue;
    		}
    		
    		if(input.equals("3")){
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			AddDirtoCurrent( Input());
    			//AddDirtoCurrent( System.console().readLine());
    			continue;
    		}
    		
    		if(input.equals("4")){ //Remove diretoria
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			RemoveDir( Input());
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
    			moveDir( Input());
    			//moveDir( System.console().readLine());
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
    	 username = Input();
    	 //username =  System.console().readLine();
    	 User user = getUserByUsername(username);
    	 if (user == null){
    		 System.out.println("Username doesn't exist");
    		 return;
    	 }
    	 System.out.println("pw:");
    	 pw = Input();
    	 //System.out.println("Password dada: " + pw);
    	 //System.out.println("Password do utilizador: " + user.getPassword());
    	 //pw =  System.console().readLine();
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
    		files.add(home);
    		getEntitySet().add(home);
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
    	 System.out.println("Vai imprimir as diretorias");
    	 for (Entity entity : this.workingDir.files) {
    		 System.out.println(entity.getFilename());
    	 }
    	 System.out.println("acabou de imprimir as diretorias");

    	 //parte do zé aqui---------------Print complexo----
    	 //System.out.println("Working directory: " + workingDir.getPath());
    	 //this.workingDir.printDir();    	 
     }
     
     
     public void RemoveDir(String dir_name){
    	 if(this.workingDir.getDirByName(dir_name)== null){//verifica se existe ou nao
    		 System.out.println("Diretoria nao existe, dentro da diretoria de trabalho!");
    		 return;
    	 }
    	 this.workingDir.DeleteEntity(dir_name);
    	 System.out.println("Directory: "+dir_name+" Removed!");
     }
     
     public void AddDirtoCurrent(String name){// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 if(this.workingDir.getDirByName(name) != null){//verifica se ja existe
    		 System.out.println("Diretoria ja existe, dentro da diretoria de trabalho!");
    		 return;
    	 }
    	 setCounter(getCounter()+1);
    	 Directory dir = new Directory(this,this.workingDir,  workingDir.getPath()+"/"+name, name, this.logged_user.getUserName(), getCounter(), 2);
    	 this.workingDir.addDir(dir);
    	 dir.setLastModified(date);
    	 files.add(dir);
    	 System.out.println("File: "+name+" created");
     }
    	
    	
    public void moveDir(String directory_destiny){ //unfinished
    	if(directory_destiny.equals(".")){//ficar na propria diretoria
    		return;
    	}
    	if(directory_destiny.equals("..")){ //voltar atras
    		if(this.workingDir.getPath().equals("/home")){
    			return;
    		}
    		this.workingDir = this.workingDir.getDirectory();
    		return;
    	}
    	Directory destiny = null;
    	destiny = this.workingDir.getDir(directory_destiny);
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
            	if(entity.getDirectory() == null){
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
    	List<Element> elList = fsDoc.getRootElement().getChildren();
    	
    	for(int i=0; i<elList.size(); i++){
    		Element user = elList.get(i);
    		//System.out.println("Lista: " + elList.get(i));
    		String username = user.getAttribute("username").getValue();
    		String name = user.getChild("name").getValue();
    		String password = user.getChild("password").getValue();
    		String homeDir = user.getChild("homeDir").getValue();
    	
    	
    	//vai dar asneira vai
    	
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