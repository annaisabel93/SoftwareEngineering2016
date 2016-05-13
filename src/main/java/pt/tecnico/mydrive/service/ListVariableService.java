package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.Variable;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.service.dto.VariableDto;

public class ListVariableService extends FileSystemService{
	
	private Login login;
	private long token;
	private String variableName;
	private String value;
	private List<VariableDto> environmentVars;

	public ListVariableService(long token, String variableName, String value) throws UnknownTokenException{
		this.token = token;
		this.variableName = variableName;
		this.value=value;
	}
	
	public String getVariableValue() {
		for (Variable v : this.login.getVariableSet()) {
			if (v.getName().equals(this.variableName)) {
				return v.getValue();
			}
		}
		return null;
	}	

	protected void print() {
		for (Variable v : getLogin(this.token).getVariableSet()) {
			if ( this.variableName.equals(null) && this.value.equals(null) ) 
				System.out.println( v.getName() + " = " + v.getValue() );
			
			if ( !this.variableName.equals(null) && this.value.equals(null) && v.getName().equals(this.variableName) ) 
				System.out.println( this.variableName + " = " + v.getValue() );
			
			if ( !this.variableName.equals(null) && !this.value.equals(null) ) 
				System.out.println( this.variableName + " = " + this.value );
		}
	}
	@Override
	protected void dispatch(){
		print();
	}
	

	

}
