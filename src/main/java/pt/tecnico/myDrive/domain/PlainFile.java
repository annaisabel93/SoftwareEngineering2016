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
	
	public void addContent(String content){
		setContent(getContent()+content);
	}
	
	
	public PlainFile(FileSystem filesystem, Document xml){
		xmlImport(xml);
		setFilesystem(filesystem);
	}
	
	public void xmlImport(Document PlainFileDoc){
		super.xmlImport(PlainFileDoc);
		try{
			setContent(new String(PlainFileDoc.getRootElement().getChild("content").getValue().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) { System.err.println(e); }
	}
	
	public Document xmlExport(){
		Element element = new Element("PlainFile");
		element.setAttribute("content", getContent());
		Document PlainFileDoc = new Document(element);
		PlainFileDoc = super.xmlExport();
		
		return PlainFileDoc;
	}
}
