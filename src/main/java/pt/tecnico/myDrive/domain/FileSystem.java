package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Scanner;
import java.util.*;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    public Directory workingDir  = null;
    public User logged_user  = null;
    public byte[] array = {0,0,0,0};

    Set<Entity> files = new HashSet<Entity>();
    Scanner keyboardSc = new Scanner(System.in);
	
    
    public FileSystem(){
    	super();
    	setCounter(0);

    	addUser("root");
    }
    
    public void setWorkingDir(Directory dir){
    	this.workingDir = dir;
    }
    
    public Directory getWorkDir(){
    	return this.workingDir;
    }
    
  // funcao para teste ler comandos de input e chamar funcoes a partir dai------------------------------------------------------------
    public void MainLoop(){
    	String input;
    	
    	while(true){
    		if(logged_user != null){
    			System.out.println("Currently loged with:"+this.logged_user.getUserName());
    			System.out.println("Working dir:"+this.workingDir.getFilename());
    		}
    		else{ System.out.println("Type 'Login' to start"); }
    		
    		input = keyboardSc.next();
    		if(input.equals("Sair")){break;} //verificar se .next() , do scanner, tambem consome o \n ou nao
    		
    		if(input.equals("Login")){ login(); continue; }
    		
    		if(input.equals("AddUser")){//so estou a deixar adicionar um user se ninguem estiver logado
    			String username;
    			if(this.logged_user != null){
    				System.out.println("Logout first");
    				continue;
    			}
    			System.out.println("Username?");
    			username = keyboardSc.next();
    			addUser(username);
    			System.out.println("Created user "+username);
    			continue;
    		}
    		
    		if(input.equals("AddDir")){
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			prepareDir(keyboardSc.next());
    			continue;
    		}
    		
    		if(input.equals("RemoveDir")){ //Nao implementado
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Directory name?");
    			//chama aqui a funcao para remover
    			//atencao que so pode remover diretorias dentro da dir atual
    			continue;
    		}
    		
    		
    		if(input.equals("Move")){ // mover entre diretorias
    			if(logged_user == null){
    				System.out.println("You must login first");
    				continue;
    			}
    			System.out.println("Ready to move");
    			moveDir(keyboardSc.next());
    			System.out.println("Moved ended");
    			continue;
    		}
    		
    		if(input.equals("Logout")){
    			this.logged_user = null;
    			this.workingDir  = null;
    			System.out.println("Logged out");
    			continue;
    		}
    	}
    	
    }
	
     //fim--------------------------------------------------------------------
    
    
     public void login(){
    	 String username;
    	 String pw;
    	 System.out.println("Username:");
    	 username = keyboardSc.next();
    	 User user = getUserByUsername(username);
    	 if (user == null){
    		 System.out.println("Username doesn't exist");
    		 return;
    	 }
    	 pw = keyboardSc.next();
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
    		user = new User(this, "root", "Super User", "***", array, username, true);
    		addUser(user);
    		//adicionar home directory e a diretoria raiz
    		setCounter(getCounter()+1);
    		//precisa de \\ para reconhecer \ dentro da string
    		Directory home =	new Directory(user,this, "\\home", "root", getCounter(), date, 2, null);
    		addEntity(home);
    		files.add(home);
    	}
    	else{
    		user = new User(this, username, username, username, array, username, false);
    		addUser(user);
    		setCounter(getCounter()+1);
    		Directory Dir =	user.addDir(user,this, this.workingDir.getFilename()+"\\"+username, username, (long)getCounter(), date, 2, this.workingDir );
    		files.add(Dir);
    		addEntity(Dir);
    	}
     }
     
     
     public void prepareDir(String name){// para ser usado de outro modo mais tarde 
    	 DateTime date = new DateTime();
    	 setCounter(getCounter()+1);
    	 Directory Dir = this.logged_user.addDir(this.logged_user,this, workingDir.getFilename()+"\\"+name, this.logged_user.getUserName(), getCounter(), date, 2, this.workingDir);
    	 files.add(Dir);
    	 addEntity(Dir);
    	 System.out.println("File: "+name+" created");
     }
    	
    	
    public void moveDir(String directory_destiny){ //unfinished
    	Directory destiny = null;
    	destiny = this.workingDir.getDir("\\"+directory_destiny);
    	
    }
    	
    
    public static FileSystem getInstance(){
    	FileSystem fs = FenixFramework.getDomainRoot().getFilesystem();
    	if(fs != null)
    		return fs;
    	else{
    		log.trace("new FileSystem");
    		return new FileSystem(0);
    	}
    }
    
    private FileSystem(int counter) {
        setRoot(FenixFramework.getDomainRoot());
        setCounter(counter);
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
    
    
    
    
    
}
