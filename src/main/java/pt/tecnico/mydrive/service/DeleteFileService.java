package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;


public class DeleteFileService extends FileSystemService {
	Login login;
	String filename;
	
	
	public DeleteFileService(long token1 , String filename) throws UnknownTokenException {
		this.login = getLogin(token1);
		this.filename = filename;
		
		login.getDirectory().getByName(filename).checkPermissions(login, "delete");
	}
	
	//executa o servico
	@Override
	protected void dispatch() throws EntityDoesNotExistException{
			if (login.getDirectory().getByName(filename) == null){
				throw new EntityDoesNotExistException(this.filename);
			}
			try {
				login.getDirectory().getByName(filename).delete();	
			} catch(EntityDoesNotExistException e) {
				throw e;
			}
	}
	
}
