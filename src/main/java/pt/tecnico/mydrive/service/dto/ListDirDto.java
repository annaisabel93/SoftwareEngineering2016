package pt.tecnico.mydrive.service.dto;

public class ListDirDto implements Comparable<ListDirDto> {

    private String info;
    private String name;


    public ListDirDto(String list) {
    	this.info = list;
    	String[] splited = list.split("\\s+");
    	this.name = splited[7];
    }

    public final String getList() {
        return this.info;
    }
    
    public final String getName() {
        return this.name;
    }

    
    @Override
    public int compareTo(ListDirDto other) {
    	return getName().compareTo(other.getName());
    }
    
}