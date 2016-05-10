package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.service.dto.VariableDto;

public class AddVariableService extends FileSystemService{
	
	private Login login;
	private long token;
	private String variableName;
	private String value;
	private List<VariableDto> environmentVars;

	public AddVariableService(long token) throws UnknownTokenException{
		this.login = getLogin(token);
		this.token = token;
	}
	
	public AddVariableService(long token, String variableName, String value) throws UnknownTokenException{
		this.login = getLogin(token);
		this.token = token;
		this.variableName = variableName;
		this.value=value;
	}
	
	@Override
	protected void dispatch(){
		
		environmentVars = new ArrayList<VariableDto>();
		
		for(Variable variable : this.login.getVariableSet()){
			environmentVars.add(new VariableDto(variable.getLogin(), variable.getName(), variable.getValue()));
		}
		Collections.sort(environmentVars);
	}
	
	public final List<VariableDto> result(){
		return environmentVars;
	}

}
