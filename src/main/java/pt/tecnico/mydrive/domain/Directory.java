package pt.tecnico.mydrive.domain;


import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;;

public class Directory extends Directory_Base {
	
	
	public Directory(String filename, User user, long id,  DateTime lastModified) {
		super();
		init(this, filename, user, id, lastModified);
	}
	
	
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
    	destiny = getDirByName(name);
    	return (Directory) destiny;
    }
    
	public void addDir(Directory dir){
		addFile(dir);
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
	
	
	public void DeleteEntity(String entity_name){ //remove uma entidade dentro da diretoria (check feito previamente)
			Entity entity = getDirByName(entity_name);
			if(entity instanceof PlainFile){
				((PlainFile) entity).Delete();
			}
			else{
				((Directory)entity).Delete();
			}
			removeFile(entity);

	}
	
	public void Delete(){
		for (Entity entity: getFileSet()) {
    		entity.Delete();
		}
		getOwner().removeFile(this);
		setOwner(null);
		setParent(null);
		//setSystem(null);
	}

	
	public Entity getByName(String name){
		for(Entity e: getFileSet()){
			if(e.getFilename().equals(name)){
				return e;
			}
		}
		return null;
	}
	
	
	public Entity getDirByName(String dir_name) {
        	for (Entity dir : getFileSet()) {
        		if (dir.getFilename().equals(dir_name)) {
        			return dir;
        		}
        	}
        return null;
    }
	
	public void printDir() {
		for(Entity entity: this.getFileSet()) {
			String type = "unknown";
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
