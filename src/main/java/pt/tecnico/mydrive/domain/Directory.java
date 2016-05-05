package pt.tecnico.mydrive.domain;


import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.InvalidPathLenghtException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class Directory extends Directory_Base {
	
	
	public Directory(String filename, User user, long id,  DateTime lastModified) {
		super();		
		init(this, filename, user, id, lastModified);
	}
	
	
    public Directory(Directory dir, String filename, User user, long id,  DateTime lastModified) {
        super();
        init(dir, filename,user,id,lastModified);
    }

    public void checkCreate(User user, String newFile) throws UserHasInvalidPermissionsException, InvalidPathLenghtException{
    	if((getPath("/"+ getFilename())+newFile).length() > 1024 ){
    		throw new InvalidPathLenghtException((getPath("/"+ getFilename())+newFile).length());
    	}
    	if(user.getName().equals(getOwner().getName())){
    		return;
    	}
    	else{
    		if(getOwner().getMask()[0] == 0){
    			throw new UserHasInvalidPermissionsException();
    		}
    		
    	}
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
    
    
    public String DateToString(DateTime time){
		String result = "";
		result = result + time.getYear()+"-"+time.getMonthOfYear()+"-"+time.getDayOfMonth()+" "+time.getHourOfDay()+":"+time.getMinuteOfHour()+":"+time.getSecondOfMinute();
		return result;
	}
    
    
    public String PermissionsToString(byte[] perm){
		String toReturn = "";
		if(perm[0] == 1){
			toReturn = toReturn + "r";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[1] == 1){
			toReturn = toReturn + "w";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[2] == 1){
			toReturn = toReturn + "x";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[3] == 1){
			toReturn = toReturn + "d";
		}
		else{
			toReturn = toReturn + "-";
		}
		return toReturn;
	}
    
    
    public int getSize(){
    	int size =  0;
    	size = getFileSet().size();
    	if(getParent().equals(this)){
    		size ++;
    	}
    	else{
    		size = size +2;
    	}
    	return size;
    	
    }
    
    public String[] list(){
    	int size = getFileSet().size() +1; //+1 por causa da propria diretoria
    	int is_root = 1;
    	if((getParent().equals(this)) == false){ //se estivermos na raiz
    		size++;
    		is_root = 0;
    	}
    	
    	String[] result = new String[size];
    	int count = 0;
    	for(Entity entity: getFileSet()) {
    		
    		result[count] = entity.checkType() + " ";
    		
    		byte[] array = entity.getOwner().getMask();
    		result[count] = result[count] + PermissionsToString(array) + " ";

    		result[count] = result[count] + entity.getSize()+ " ";
    		result[count] = result[count] + entity.getOwner().getUserName() + " ";
    		result[count] = result[count] + entity.getId() + " ";
    		result[count] = result[count] + DateToString(entity.getLastModified()) + " ";
    		result[count] = result[count] + entity.getFilename();
    	
    		count++;	
    	}
    	//propria diretoria
    	result[count] = "dir ";
		result[count] = result[count] + PermissionsToString(getPermissions()) + " ";
		result[count] = result[count] + getSize()+ " ";
		result[count] = result[count] + getOwner().getUserName() + " ";
		result[count] = result[count] + getId() + " ";
		result[count] = result[count] + DateToString(getLastModified()) + " ";
		result[count] = result[count] + getFilename();
    	count++;
		//diretoria mae, se nao for a raiz
		if(is_root == 0){
			result[count] = "dir ";
			result[count] = result[count] + PermissionsToString(getParent().getPermissions()) + " ";
			result[count] = result[count] + getParent().getSize()+ " ";
			result[count] = result[count] + getParent().getOwner().getUserName() + " ";
			result[count] = result[count] + getParent().getId() + " ";
			result[count] = result[count] + DateToString(getParent().getLastModified()) + " ";
			result[count] = result[count] + getParent().getFilename();
		}
		
		
    	return result;
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

	
	public Entity getByName(String name) throws EntityDoesNotExistException {
		for(Entity e: getFileSet()){
			
			if(e.getFilename().equals(name)){
				return e;
			}
		}
		throw new EntityDoesNotExistException(name);
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
