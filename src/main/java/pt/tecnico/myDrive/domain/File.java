package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

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
    
}