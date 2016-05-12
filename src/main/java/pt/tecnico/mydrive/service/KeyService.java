package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.Login;

public class KeyService extends FileSystemService {

	private long token;
	private Login login;
	private String username;
	private String password;
	
	public long getToken() {
		return token;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public KeyService(long token) {
		this.token = token;
	}

	@Override
	protected void dispatch() {
		this.login = getLogin(token);
		this.username = this.login.getUser().getUserName();
		this.password = this.login.getUser().getPassword();
	}

}
