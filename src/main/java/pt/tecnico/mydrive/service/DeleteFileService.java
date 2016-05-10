package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.UnknownTokenException;


public class DeleteFileService extends FileSystemService {
	Login login;
	String filename;
	
	
	public DeleteFileService(long token1 , String filename) throws UnknownTokenException {
		this.login = getLogin(token1);
		this.filename = filename;
		
		login.getDirectory().getByName(filename).checkPermissions(this.login.getUser(), "delete");
	}
	
	//executa o servico
	
	@Override
	protected void dispatch() throws EntityDoesNotExistException{
			try {
				login.getDirectory().getByName(filename).delete();	
			} catch(EntityDoesNotExistException e) {
				throw e;
			}
	}
	
}
