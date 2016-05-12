package pt.tecnico.mydrive.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.exception.UserHasInvalidPermissionsException;

public class ExecuteFileService extends FileSystemService {

	private static Login login;
	private long token;
	private String path;
	private List<Entity> arguments;
	private String path_until_now;
	private static final byte execute = login.getUser().getMask()[2];
	private String result;
	private String content;
	private String[] args;


	public void ExecuteFileService(long token, String path, List<Entity> arguments) {
		this.login = getLogin(token);
		this.path = login.getDirectory().getPath(path_until_now);
		this.arguments = arguments;
		this.token = token;
	}

	public String getResult(){
		return this.result;
	}
	//FIXME needs to receive content from presentation part
	@Override
	protected void dispatch() throws UserHasInvalidPermissionsException{
		if (execute == 1) {
			List<Entity> arguments = this.arguments;
			String Type = null;

			for (Entity f : arguments){
				Type = f.checkType();
				String name = f.getFilename();
					String newf = f.execute();
					//é aqui dentro que sºao as cenas

					//file f = getFile("/home/ana/file")
					//f.execute

					if(f.execute().getClass().equals("App")){
						try {
							App.execute(content, args);
						} catch (ClassNotFoundException | SecurityException | NoSuchMethodException
								| IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
							System.out.println("Cannot run an App like that!");
						}
					}

					if (f.execute().getClass().equals("Link")){
						ReadFileService read = new ReadFileService(this.token, name);
						read.execute();
						this.result = read.getResult();
						//é aqui dentro que sºao as cenas

						//file f = getFile("/home/ana/file")
						//f.execute
					}
					
					if (Type.equals("link")){
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
