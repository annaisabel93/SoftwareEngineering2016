package pt.tecnico.mydrive.service;

public class ResolveVariableService extends FileSystemService{
	
	private String cpath;
	private String path;
	
    public String convert(String path){   	
		return cpath;
    }
    
	public ResolveVariableService (String path){
		this.path = path;
		convert(path);
	}


	@Override
	protected void dispatch() {
	}
	
	
	

    
}
