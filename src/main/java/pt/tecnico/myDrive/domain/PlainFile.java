package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile(String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute, String content) {
        super();
        setContent(content);
    }

	public PlainFile() {
		// TODO Auto-generated constructor stub
	}
    
}
