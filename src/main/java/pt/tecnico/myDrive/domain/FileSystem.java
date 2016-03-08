package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Scanner;

import pt.ist.fenixframework.FenixFramework;

public class FileSystem extends FileSystem_Base {
    static final Logger log = LogManager.getRootLogger();
    
    
    public Directory workingDir;
    
    public FileSystem(){
    	super();
    	byte[] array = {0,0,0,0};
    	DateTime date = new DateTime();
    	
    	User root = new User(this, "root", "root", "rootroot", array, "root", true);
    	Directory rootDir = new Directory(root, "root", "root", getCounter()+1, date, 2, false, false, false, false);
    	//no momento de criacao vai ter dimensao 2: "." e ".." - as diretorias sempre presentes
    	setWorkingDir(rootDir); //define a diretoria de trabalho atual (a ser modificada conforme o user queira mudar)
    }
    
    public void setWorkingDir(Directory dir){
    	this.workingDir = dir;
    }
    
    public Directory getWorkDir(){
    	return this.workingDir;
    }
    
  // funcao para teste (ler comandos de input e chamar funcoes a partir dai------------------------------------------------------------
    public void testLoop(){
    	
    	Scanner keyboardSc = new Scanner(System.in);
    	String input;
    	while(true){
    		input = keyboardSc.next();
    		if(input.equals("Sair\n")){break;}
    	}
    }
	
     //fim--------------------------------------------------------------------
    
    
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
    
}
