package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.UsernameDoesntExistException;
import pt.tecnico.mydrive.service.dto.*;

public class ListUsersService extends FileSystemService {

	FileSystem fs = FileSystem.getInstance();
	private List<UsersDto> users;
	private String username;
	private Login login;
	private long token;
	

	public ListUsersService(long token, String username) {
		this.username = username;
		this.login = getLogin(token);
		this.token = token;
	}

	public final void dispatch() throws UsernameDoesntExistException {
		User user = fs.getUserByUsername(username);
		users = new ArrayList<UsersDto>();

		for (User u : fs.getUserSet()) {
			if (u instanceof User)
				users.add(new UsersDto(u.getLoginbyToken(this.token), u.getUserName(), u.getPassword()));
		}

		Collections.sort(users);
	}

	public final List<UsersDto> result() {
		return users;
	}
}
