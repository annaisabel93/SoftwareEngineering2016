package pt.tecnico.mydrive.service;

import java.lang.reflect.InvocationTargetException;

import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.Login;

public class ExecuteFileService extends FileSystemService {

	private long token;
	private String path;
	private String path_until_now;
	private byte execute;
	private String result;
	private String[] args;
	private Login login;


	public ExecuteFileService(long token, String path, String[] args) {
		this.login = getLogin(token);
		this.path = login.getDirectory().getPath(path_until_now);
		this.args = args;
		this.token = token;
		this.execute = login.getUser().getMask()[2];
	}

	public String getResult(){
		return this.result;
	}
	//FIXME needs to receive content from presentation part

	@Override
	protected void dispatch(){
		if (execute == 1) {
			String newpath = null;
			String fn = null;

			String[] split = path.split("/");

			for (int i = 0; i < split.length - 1; i++) {
				newpath = newpath + "/" + split[i];
			}

			fn = split[split.length - 1];


			ChangeDirectoryService change = new ChangeDirectoryService(this.token, newpath);
			Entity file = this.login.getDirectory().getByName(fn);
			//é aqui dentro que são as cenas

			//file f = getFile("/home/ana/file")
			//f.execute
			try {
				file.execute(fn, args);
			} catch (ClassNotFoundException | SecurityException | NoSuchMethodException
					| IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			}

		}

	}
}

