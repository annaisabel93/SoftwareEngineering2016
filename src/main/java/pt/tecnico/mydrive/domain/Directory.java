package pt.tecnico.mydrive.domain;

import java.util.ArrayList;

import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;

public class Directory extends Directory_Base {
	
	public ArrayList<Directory> diretorias = new ArrayList<Directory>();
	public ArrayList<PlainFile> plains = new ArrayList<PlainFile>();
	public ArrayList<Link> links = new ArrayList<Link>();
	public ArrayList<App> apps = new ArrayList<App>();
	
	
    public Directory(Directory dir, String filename, User user, long id,  DateTime lastModified) {
        super();
        init(dir, filename,user,id,lastModified);
    }

	
    public Directory(User owner, Element xml){
    	xmlImport(xml);
    	//setUser(user);
    	setOwner(owner);
    }

    public Directory getDir(String name){
    	Entity destiny = null;
    	destiny = getDirByName(name,"directory");
    	return (Directory) destiny;
    }
    
	public void addDir(Directory dir){
		diretorias.add(dir);
		addFile(dir);
	}
	
	public void addPlainFile(PlainFile text){
		plains.add(text);
		addFile(text);
	}
	
	public void WriteToFile(String content, String filename)throws TexFileDoesNotExistException{
		for (Entity entidade : getFileSet()) {
   		 if(entidade.getFilename().equals(filename) && (entidade instanceof PlainFile)){
   			 ((PlainFile) entidade).addContent(content); //cast para poder executar o metodo
   			 return;
   		 }
   	 }
   	
   	 throw new TexFileDoesNotExistException(filename);
	}
	
	
	public void DeleteEntity(String entity_name, String type){ //remove uma entidade dentro da diretoria (check feito previamente)
		
		if(type.equals("Directory")){
			Directory directory = (Directory) getDirByName(entity_name, "directory");
			//System.out.println("e mesmo o que procuro?" + directory.getFilename());
			diretorias.remove(directory);
			directory.Delete();
			//System.out.println("Diretorio vazio?" +getFileSet().isEmpty());//esta vazio!!!!!!!!!!!
			removeFile(directory);
			//System.out.println("Diretorio vazio?" +getFileSet().isEmpty());//esta vazio!!!!!!!!!!!
		
		}
		else if(type.equals("Plain_File")){
			Entity entity = getDirByName(entity_name,"plainfile");
			plains.remove(entity);
			removeFile(entity);
		}
		else if(type.equals("Link")){
			Entity entity = getDirByName(entity_name,"link");
			links.remove(entity);
			removeFile(entity);
		}
		else if(type.equals("App")){
			Entity entity = getDirByName(entity_name,"app");
			apps.remove(entity);
			removeFile(entity);
		}
	}
	
	public void Delete(){
		for (Entity entity: getFileSet()) {
    		entity.Delete();
		}
		//System.out.println("Vai apagar-se"+ this.getFilename());
		this.diretorias.clear();
		this.links.clear();
		this.apps.clear();
		this.plains.clear();
		//System.out.println("este dono ainda tem este objeto? " + getOwner().getFileSet().contains(this));
		getOwner().removeFile(this);
		setOwner(null);
		setParent(null);
		//setSystem(null);
	}

	public Entity getDirByName(String dir_name, String type) {
        if(type.equals("directory")){
        	for (Directory dir : diretorias) {
        		if (dir.getFilename().equals(dir_name)) {
        			return dir;
        		}
        	}
        }
        if(type.equals("plainfile")){
        	for (PlainFile plain : plains) {
        		if (plain.getFilename().equals(dir_name)) {
        			return plain;
        		}
        	}
        }
        
        if(type.equals("link")){
        	for (Link link : links) {
        		if (link.getFilename().equals(dir_name)) {
        			return link;
        		}
        	}
        }
        
        if(type.equals("app")){
        	for (App app : apps) {
        		if (app.getFilename().equals(dir_name)) {
        			return app;
        		}
        	}
        }
        return null;
    }
	
	public void printDir() {
		for(Entity entity: this.diretorias) {
			String type = "unknown";
			//String[] parts = entity.getClass().toString().split("\\."); //detect type FIX?
			//type = parts[4];
			if(entity instanceof Directory) { type = "Directory"; }
			else if(entity instanceof App){ type = "App"; }
			else if(entity instanceof Link){ type = "Link"; }
			else if(entity instanceof PlainFile){ type = "PlainFile"; }
			System.out.println(type + " " + "permissions" + " " + entity.getOwner() + " " + entity.getId() + " " + entity.getLastModified() + " " + entity.getFilename()); //FIXME: add permissions
		}
	}
	
	public void xmlImport(Element directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Element xmlExport(){
		Element element = new Element("Directory");
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 	    	
		element.addContent(new Element ("filename").setText(getFilename())); 	
		//element.addContent(new Element ("owner").setText(getOwner()));
		//element.addContent(new Element("path").setText(getPath()));
		return element;
	}
	
}
