package pt.tecnico.myDrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Entity extends Entity_Base {
    
	public Entity(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute) {
        super();
        setFilename(filename);
        setOwner(owner);
        setId(id);
        setLastModified(lastModified);
        setDimension(dimension);
        setRead(read);
        setWrite(write);
        setDelete(delete);
        setExecute(execute);
        setFilesystem(filesystem);
        //FIXME do we need a path? - enunciado
    }

	public Entity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setFilesystem(FileSystem fs){
		if(fs == null)
			super.setFilesystem(null);
		else{
			fs.addEntity(this);
		}
	}
	
	public void xmlImport(Document filedoc){
		try {
			setFilename(new String(filedoc.getRootElement().getAttribute("filename").getValue().getBytes("UTF-8")));
			setOwner(new String(filedoc.getRootElement().getAttribute("owner").getValue().getBytes("UTF-8")));
//			setRead(new Boolean(filedoc.getRootElement().getAttribute("read").getValue()));
//			setWrite(new Boolean(filedoc.getRootElement().getAttribute("write").getValue()));
//			setDelete(new Boolean(filedoc.getRootElement().getAttribute("delete").getValue()));
//			setExecute(new Boolean(filedoc.getRootElement().getAttribute("execute").getValue()));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		}
	}
	
	public Document xmlExport(){
		    	Element file = new Element("File");
		    	file.setAttribute("filename", getFilename()); 	
		    	file.setAttribute("owner", getOwner());
//		    	element.setAttribute("read", Boolean.toString(getRead()));
//		    	element.setAttribute("write", Boolean.toString(getWrite()));
//		    	element.setAttribute("delete", Boolean.toString(getDelete()));
//		    	element.setAttribute("execute", Boolean.toString(getExecute()));
		    	Document document = new Document(file);
				return document;    
	}
    
}
