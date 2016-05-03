package pt.tecnico.mydrive.service.dto;

import pt.tecnico.mydrive.domain.Login;

public class VariableDto implements Comparable<VariableDto>{
	private Login login;
	private String name;
	private String value;
	
	public VariableDto(Login login, String name, String value){
		this.login = login;
		this.name = name;
		this.value = value;				
	}
	
	public final Login getLogin(){
		return this.login;
	}
	
	public final String getName(){
		return this.name;
	}
	
	public final String getValue(){
		return this.value;
	}
	
	@Override
	public int compareTo(VariableDto other) {
		return getName().compareTo(other.getName());
	}

}
