package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile(FileSystem filesystem, Directory parent,String filename, User user, long id, DateTime lastModified,  String content) {
        super();
        this.initPlainFile(filesystem,parent, filename,user,id,lastModified,content);
        
    }

	public void initPlainFile(FileSystem filesystem, Directory dir, String filename, User user, long id, DateTime lastModified, String content){
		init(filesystem,null,filename,user,id,lastModified);
		setContent(content);
	}

	
	public String getMyType(){
		return "PlainFile";
	}

	public PlainFile() {
		// TODO Auto-generated constructor stub
	}
	
	public PlainFile(User owner, Element xml){
		xmlImport(xml);
		setOwner(owner);
	}
	public void addContent(String content){
		setContent(getContent()+content);
	}
	


	public void xmlImport(Element PlainFileDoc){
		super.xmlImport(PlainFileDoc);
		
			setContent(new String(PlainFileDoc.getChild("content").getValue()));
		
	}
	
	public Element xmlExport(){
		Element element = new Element("PlainFile");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 	    	
		element.addContent(new Element ("filename").setText(getFilename())); 	
		//element.addContent(new Element ("owner").setText(getOwner()));
		//element.addContent(new Element("path").setText(getPath()));
		Document PlainFileDoc = new Document(element);
		
		return element;
	}
}
