package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;

public class File extends File_Base {
    
   public File(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute) {
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
    }

	public File() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setFilesystem(FileSystem fs){
		if(fs == null)
			super.setFilesystem(null);
		else{
			fs.addFile(this);
		}
	}
	
	public void xmlImport(Document filedoc){
		try {
			setFilename(new String(filedoc.getRootElement().getAttribute("filename").getValue().getBytes("UTF-8")));
			setOwner(new String(filedoc.getRootElement().getAttribute("owner").getValue().getBytes("UTF-8")));
			setRead(new Boolean(filedoc.getRootElement().getAttribute("read").getValue())); //oh booleans, what are we going to do with you
			setWrite(new Boolean(filedoc.getRootElement().getAttribute("write").getValue()));
			setDelete(new Boolean(filedoc.getRootElement().getAttribute("delete").getValue()));
			setExecute(new Boolean(filedoc.getRootElement().getAttribute("execute").getValue()));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		}
	}
	
	public Document xmlExport(){
		    	Element element = new Element("File");
		    	element.setAttribute("filename", getFilename()); 	
		    	element.setAttribute("owner", getOwner());
		    	//how to add booleans if setAttribute needs strings?
		    	Document document = new Document(element);
				return document;    
	}
}