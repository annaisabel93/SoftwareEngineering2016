package pt.tecnico.mydrive.service;

public class ReadFileService extends FileSystemService{
	
	private long token;
	private String filename;
	
	public ReadFileService(long token, String filename){
		this.token = token;
		this.filename = filename;
	}
	
	@Override
	public final void dispatch(){
		//--FIXME
		//TODO--verificar se o tipo de ficheiro tem conteudo, ou seja, se nao e Directory
	}
}
