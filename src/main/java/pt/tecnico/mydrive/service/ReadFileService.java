package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.WrongFileTypeException;

public class ReadFileService extends FileSystemService{
	
	private long token;
	private Login login;
	private String filename;
	
	public ReadFileService(long token, String filename){
		this.login = getLogin(token);
		this.filename = filename;
		this.token = token;
	}
	
	public long getToken(){
		return this.token;
	}
	
	public String getFilename(){
		return this.filename;
	}
	
	public String returnContent(String filename) throws WrongFileTypeException{
		User user = getLogin(getToken()).getUser();
		Entity file = user.getEntityByName(filename);
		if(!(file instanceof PlainFile)){
			throw new WrongFileTypeException();
		}
		return ((PlainFile) file).getContent();	
	}
		
		//ir buscar a mask to user
		//com a mascara do user verificar as permissoes que o user tem para ler files
	
	
	@Override
	public final void dispatch(){
		try {
			returnContent(getFilename());
		} catch (WrongFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//--FIXME
		//TODO--verificar se o tipo de ficheiro tem conteudo, ou seja, se nao e Directory
		
	} 

}
