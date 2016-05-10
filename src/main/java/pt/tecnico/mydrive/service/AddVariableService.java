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

	public AddVariableService(long token, String variableName) throws UnknownTokenException{
		this.login = getLogin(token);
		this.token = token;
		this.variableName = variableName;
	}

	public AddVariableService(long token, String variableName, String value) throws UnknownTokenException{
		this.login = getLogin(token);
		this.token = token;
		this.variableName = variableName;
		this.value=value;
	}
	
	public String getAssociatedValue() {
		for (Variable v : this.login.getVariableSet()) {
			if (v.getName().equals(this.variableName)) {
				return v.getValue();
			}
		}
		return null;
	}	

	@Override
	protected void dispatch(){
		
		environmentVars = new ArrayList<VariableDto>();
		
		for(Variable variable : this.login.getVariableSet()){
			//if the variable already exists, i will only change its value
			if ( this.variableName.equals(variable.getName()) ) {
				if ( !this.value.equals(null) ) 
					variable.setValue(this.value);
					
				environmentVars.add(new VariableDto(variable.getLogin(), variable.getName(), variable.getValue()));	
				//environmentVars.add(new VariableDto(variable.getLogin(), variable.getName(), variable.getValue()));
			} else {
			//if the variable does not exist, i will create it
				new Variable(this.login, this.variableName, this.value);
				environmentVars.add(new VariableDto(this.login, this.variableName, this.value));
			}
		}
		Collections.sort(environmentVars);
	}
	
	public final List<VariableDto> result(){
		return environmentVars;
	}

}
