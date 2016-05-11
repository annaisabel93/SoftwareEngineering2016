package pt.tecnico.mydrive.service;

import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.Variable;

public class ReplaceVariableService extends FileSystemService{
	private Login login;
	private String filename;
	private long token;
	private Variable variable;
	private String value;
	private Link link;
	private FileSystem fs;
	private String content;
	private String[] result;

	
	public ReplaceVariableService (long token, Link link, Variable var){
		
		this.login = getLogin(token);
		this.value = var.getValue();
		this.variable = var;
		this.link = link;
		
		content = link.getContent();
		String[] parts = content.split("/");
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].contains("$")){
				parts[i].replace(parts[i], value);
				this.result[i]+=parts[i];
			}
			this.result[i]+=parts[i];
		}
	}


	@Override
	protected void dispatch() {
		
	}
	
    public String[] getResult(){
    	return this.result;
    }
}
