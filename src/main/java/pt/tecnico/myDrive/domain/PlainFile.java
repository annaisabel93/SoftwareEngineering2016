package pt.tecnico.myDrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile(FileSystem filesystem, String filename, String owner, long id, DateTime lastModified, int dimension, String content) {
        super();
        setContent(content);
        setFilesystem(filesystem);
        setFilename(filename);
        setOwner(owner);
        setId(id);
        setLastModified(lastModified);
        setDimension(dimension);
        
    }

	public PlainFile() {
		// TODO Auto-generated constructor stub
	}
	
	public PlainFile(FileSystem filesystem, Element xml){
		xmlImport(xml);
		setFilesystem(filesystem);
	}
	public void addContent(String content){
		setContent(getContent()+content);
	}
	


	public void xmlImport(Element PlainFileDoc){
		super.xmlImport(PlainFileDoc);
		
			setContent(new String(PlainFileDoc.getChild("content").getValue()));
		
	}
	
	public Element xmlExport(){
//		Element element = new Element("PlainFile");
//		element.setAttribute("content", getContent());
//		Document PlainFileDoc = new Document(element);
//		PlainFileDoc = super.xmlExport();
		Element element = new Element("PlainFile");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 	    	
		element.addContent(new Element ("filename").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner()));
		element.addContent(new Element("path").setText(getPath()));
		Document PlainFileDoc = new Document(element);
		
		return element;
	}
}
