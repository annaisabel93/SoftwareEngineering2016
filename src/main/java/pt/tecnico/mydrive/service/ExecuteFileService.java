package pt.tecnico.mydrive.service;

import java.util.List;
import java.util.Set;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class ExecuteFileService extends FileSystemService {

	private static Login login;
	private long token;
	private String path;
	private List<Entity> arguments;
	private String path_until_now;
	private static final byte execute = login.getUser().getMask()[2];
	private String result;


	public void ExecuteFileService(long token, String path, List<Entity> arguments) {
		this.login = getLogin(token);
		this.path = login.getDirectory().getPath(path_until_now);
		this.arguments = arguments;
		this.token = token;
	}

	public String getResult(){
		return this.result;
	}


	@Override
	protected void dispatch() throws UserHasInvalidPermissionsException{
		if (execute == 1) {
			List<Entity> arguments = this.arguments;
			String Type = null;

			for (Entity f : arguments){
				Type = f.checkType();
				String name = f.getFilename();
				if(Type == "app"){
					ReadFileService read = new ReadFileService(this.token, name);
					read.execute();
					//TODO
				}

				if (Type == "plainFile"){
					ReadFileService read = new ReadFileService(this.token, name);
					read.execute();
					this.result = read.getResult();
				}

				if (Type == "link"){
					String newPlace;
					ReadFileService read = new ReadFileService(this.token, name);
					read.execute();
					newPlace = read.getResult();
					ChangeDirectoryService change = new ChangeDirectoryService(this.token, newPlace);
					change.execute();
				}
			}

		}
		
		else throw new UserHasInvalidPermissionsException();

	}


}
