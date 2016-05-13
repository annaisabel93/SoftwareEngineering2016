package pt.tecnico.mydrive.service.dto;
import pt.tecnico.mydrive.domain.Login;


public class UsersDto implements Comparable<UsersDto>{

	private Login login;
	private String name;
	private String password;

	public UsersDto(Login login, String name, String password){
		this.login = login;
		this.name = name;		
		this.password = password;
	}

	public final Login getLogin(){
		return this.login;
	}

	public final String getName(){
		return this.name;
	}
	
	public final String getPassword(){
		return this.password;
	}

	@Override
	public int compareTo(UsersDto other) {
		return getName().compareTo(other.getName());
	}

}

