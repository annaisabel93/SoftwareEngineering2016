package pt.tecnico.myDrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class Entity extends Entity_Base {
    
	public Entity(FileSystem filesystem, Directory dir, String path, String filename, String owner, long id, int dimension) {
        super();
        setFilename(filename);
        setOwner(owner);
        setId(id);
        setDimension(dimension);
        setFilesystem(filesystem);
        setPath(path);
        setDirectory(dir);
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
			setPath(new String (filedoc.getRootElement().getAttribute("path").getValue().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		}
	}
	
	public Document xmlExport(){
		    	Element file = new Element("File");
		    	file.setAttribute("filename", getFilename()); 	
		    	file.setAttribute("owner", getOwner());
		    	file.setAttribute("path", getPath());
		    	Document document = new Document(file);
				return document;    
	}
    
}
