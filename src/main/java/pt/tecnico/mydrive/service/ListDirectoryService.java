package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.UnknownTokenException;
import pt.tecnico.mydrive.service.dto.ListDirDto;


public class ListDirectoryService extends FileSystemService {

    private Login login;
    private List<ListDirDto> dto;
    private String[] result;
    private long token;
   
    private Directory workingDir;

//Tem que ir buscar um login, para saber a diretoria de trabalho
    
    public ListDirectoryService(long token) throws UnknownTokenException{
    	this.token = token;
     
        
    }

    @Override
    public final void dispatch() throws DirectoryDoesNotExistWithinDirectoryException {
    	this.login = getLogin(this.token);
    	this.workingDir = this.login.getDirectory();
    	String[] result1  = this.workingDir.list();
    	this.result =  result1;
    	dto = new ArrayList<ListDirDto>();
    	for(String line: result1){
    		dto.add(new ListDirDto(line));
    	}
    	Collections.sort(this.dto);
    	for(ListDirDto dtosingle: dto){
    		System.out.println(dtosingle.getList());
    	}
    	
    }
    
    public List<ListDirDto> getDto(){
    	return this.dto;
    }
    
    public String[] getResult(){
    	return this.result;
    }
    
    
}