package pt.tecnico.mydrive.domain;


import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;

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
   			 ((PlainFile) entidade).addContent(getLogin(), content); //cast para poder executar o metodo
   			 return;
   		 }
   	 }
   	
   	 throw new TexFileDoesNotExistException(filename);
	}
	

	@Override
	public void delete(){
		for (Entity entity: getFileSet()) {
    		entity.delete();
    		removeFile(entity);
		}
		setLogin(null);
		
		super.delete();
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
//			String type = "unknown";
//			if(entity instanceof Directory) { type = "dir"; }
//			else if(entity instanceof App){ type = "app"; }
//			else if(entity instanceof Link){ type = "link"; }
//			else if(entity instanceof PlainFile){ type = "plainFile"; }
			byte [] array = entity.getPermissions();
			System.out.println("name: "+ entity.getFilename() + " " + "permissions: " + array[0]+array[1]+array[2]+array[3]+" Owner: " + entity.getOwner().getUserName() + " " + entity.getId() + " Last Modified: " + entity.getLastModified());
		}
	}
	
	
	@Override
	public String checkType(){
		return "dir";
	}
	
	public void xmlImport(Element directoryDoc){
		super.xmlImport(directoryDoc);
	}
	
	public Element xmlExport(){
		Element element = new Element("dir");
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 	
		element.addContent(new Element ("path").setText(getPath("")));
		element.addContent(new Element ("name").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner().getUserName()));
		
		if (getOwner().getUserName().equals("root")){
			element.addContent(new Element("perm").setText("----")); //dirty hack, can't have mask working
		}
//		else
//		{
//			System.out.println(getOwner().getUserName());
//			element.addContent(new Element("perm").setText(getPermissions().toString()));
//		
//		}
		return element;
	}
	
}
