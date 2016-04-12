package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.domain.FileSystem;

public class ChangeDirectoryService extends FileSystemService {

    private Login login;
    private String directoryPath;
    private Directory workingDir;
    private FileSystem fs;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ChangeDirectoryService(long token, String dirpath) throws UnknownTokenException{
    	this.login = getLogin(token);
        this.directoryPath = dirpath;
        this.workingDir = this.login.getDirectory();
        this.fs = getFileSystem();
    }

    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
    	if(directoryPath.equals(".")){ //Print current directory
    		System.out.println(this.workingDir.getPath(""));
    		return;
    	}
    	if(directoryPath.equals("..")) { //Goes to parent directory
    		if(this.workingDir.getParent() == this.workingDir){
    			System.out.println(this.workingDir.getPath(""));
    			return;
    		}
    		login.setDirectory(this.workingDir.getParent());
    		System.out.println(this.workingDir.getPath(""));
    		return;
    	}
    	String[] items= this.directoryPath.split("/");
    	if(items[0].equals("")){
    		moveAbsolute(this.directoryPath);
    		return;
    	}
    	else{
    		moveRelative(this.directoryPath);
    		return;
    	}
    	
    }
    	
    
    public void moveAbsolute(String absolutePath) throws DirectoryDoesNotExistWithinDirectoryException{ //Goes from rootDir until the desired Directory
    	Directory root = fs.getRootDir(); //Starts on rootDir
    	String[] items= this.directoryPath.split("/"); //splits the full path into Directories
    	for(int x = 1; x < items.length; x++){ // Goes to each array position to move 1 by 1 until the last position (destiny)
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
    			throw new DirectoryDoesNotExistWithinDirectoryException(items[x]);
    		}
    		root = dir;
    	}
    	login.setDirectory(root);
    	System.out.println(root.getPath(""));
    }
    
    public void moveRelative(String relativePath){// igual, mas parte do current directory
    	Directory root = this.login.getDirectory(); 
    	String[] items = this.directoryPath.split("/"); 
    	for(int x = 0; x < items.length; x++){
    		Directory dir = (Directory) root.getByName(items[x]);
    		if(dir == null){
    			throw new DirectoryDoesNotExistWithinDirectoryException(items[x]);
    		}
    		root = dir;
    	}
    	login.setDirectory(root);
    	System.out.println(root.getPath(""));
    }
    
    
    
}