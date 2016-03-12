package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Scanner;
import java.util.ArrayList;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.UsernameAlreadyExistsException;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    
    public Directory workingDir  = null;
    public User logged_user  = null;
    public byte[] array = {0,0,0,0}; //mask - ainda nao esta bem implementada

    ArrayList<File> files = new ArrayList<File>();
    Scanner keyboardSc = new Scanner(System.in);
	
    
    public FileSystem(){
    	super();
    	setCounter(0);
    	//DateTime date = new DateTime();
    	
    	
    	addUser("root", "root", "rootroot");
    	//User root = new User(this, "root", "root", "rootroot", array, "root", true, getCounter()+1);
    	//setCounter(get)
    	//Directory rootDir = new Directory(root,this, "root", "root", getCounter()+1, date, 2, false, false, false, false);
    	//setCounter(getCounter()+1);
    	//no momento de criacao vai ter dimensao 2: "." e ".." - as diretorias sempre presentes
    	// setWorkingDir(rootDir);
    }
    
    public void setWorkingDir(Directory dir){
    	this.workingDir = dir;
    }
    
    public Directory getWorkDir(){
    	return this.workingDir;
    }
    
  // funcao para teste ler comandos de input e chamar funcoes a partir dai------------------------------------------------------------
    public void testLoop(){
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
    		
    		if(input.equals("AddUser")){
    			String username;
    			String name;
    			String pw;
    			if(this.logged_user != null){
    				System.out.println("Logout first");
    				continue;
    			}
    			System.out.println("Username?");
    			username = keyboardSc.next();
    			System.out.println("Name?");
    			name = keyboardSc.next();
    			System.out.println("pw?");
    			pw = keyboardSc.next();
    			addUser(username, name, pw);
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
    			prepareDir(keyboardSc.next());
    			//chama aqui a funcao para remover
    			//atencao que so pode remover diretorias dentro da dir atual
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
     
     
     public void addUser(String username, String name, String pw){
    	User user = null;
    	DateTime date = new DateTime();
    	if(this.logged_user == null){//caso root - apenas ira executar 1 vez
    		user = new User(this, "root", "root", "rootroot", array, username, true);
    	}
    	else{
    		user = new User(this, name, username, pw, array, username, false);
    	}
     	setCounter(getCounter()+1);
     	Directory Dir =	user.addDir(user,this, username, username, getCounter(), date, 2, false, false, false, false);
     	files.add(Dir);
     }
     
     
     public void prepareDir(String name){// para ser usado de outro modo mais tarde quando implementarmos as permissoes bem
    	 DateTime date = new DateTime();
    	 setCounter(getCounter()+1);
    	 Directory Dir =	this.logged_user.addDir(this.logged_user,this, name, this.logged_user.getUserName(), getCounter(), date, 2, false, false, false, false);
    	 files.add(Dir);
    	 System.out.println("File: "+name+" created");
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
