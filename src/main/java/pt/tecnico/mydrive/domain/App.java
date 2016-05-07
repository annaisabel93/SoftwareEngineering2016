package pt.tecnico.mydrive.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.InvalidAppException;
public class App extends App_Base {

	public App(Directory dir, String filename, User user, long id, DateTime lastModified, String content) {
		super();
		init(dir, filename,user,id,lastModified);
		setContent(content);
	}
	
	public static void run(String content, String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> cls;
		Method meth;
		try {
			cls = Class.forName(content);
			meth = cls.getMethod("main", String[].class);
		} catch (ClassNotFoundException cnfe) { // name is a method
			int pos;
			if ((pos = content.lastIndexOf('.')) < 0) throw cnfe;
			cls = Class.forName(content.substring(0, pos));
			meth = cls.getMethod(content.substring(pos+1), String[].class);
		}
		meth.invoke(null, (Object)args);
	}

	public App(User owner, Element xml){
		xmlImport(xml);
		setOwner(owner);
	}

	public void xmlImport(Element appDoc){
		super.xmlImport(appDoc);
	}

	public int getSize(){
		return getContent().length();
	}

	public String checkType(){
		return "app";
	}

	public Element xmlExport(){
		Element element = new Element("app");
		element.setAttribute("content", getContent());
		String str = String.format ("%d", getId());
		element.setAttribute("id", str); 
		element.addContent(new Element ("path").setText(getPath("")));
		element.addContent(new Element ("name").setText(getFilename())); 	
		element.addContent(new Element ("owner").setText(getOwner().toString()));
		element.addContent(new Element ("method").setText(getContent()));

		if (getOwner().getUserName().equals("root")){ 	//FIXME
			element.addContent(new Element("perm").setText("----")); //dirty hack, can't have mask working
		}

		return element;		

	}
}
