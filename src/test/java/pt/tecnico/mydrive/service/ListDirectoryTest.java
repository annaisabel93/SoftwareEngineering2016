package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.service.dto.ListDirDto;


public class ListDirectoryTest extends AbstractServiceTest {
	
	private Login login;
	private long token;
	private App app;
	private Directory dir;
	private Link link;
	private PlainFile plain;
	
	
	
	protected void populate(){
		DateTime date = new DateTime();
		FileSystem fs =FileSystem.getInstance();
		User usr = new User(fs, "Luis", "luissantos", "luis1234", new byte[] {1,1,1,1} , "/home/luissantos");
		LoginService service = new LoginService("luissantos", "luis1234");
		service.execute();
		this.token =  service.getToken();
		Login login = usr.getLoginbyToken(service.getToken());
		this.login = login;
		this.dir = new Directory(login.getDirectory(), "dir", usr, 1000, date);
		this.plain = new PlainFile(login.getDirectory(), "text", usr, 1, date, "conteudo");
		this.app = new App(login.getDirectory(), "app", usr, 2, date, "my app");
		this.link = new Link(login.getDirectory(), "link", usr, 3, date, "link to teste");
		
	}
	
	public String PermissionsToString(byte[] perm){
		String toReturn = "";
		if(perm[0] == 1){
			toReturn = toReturn + "r";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[1] == 1){
			toReturn = toReturn + "w";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[2] == 1){
			toReturn = toReturn + "x";
		}
		else{
			toReturn = toReturn + "-";
		}
		if(perm[3] == 1){
			toReturn = toReturn + "d";
		}
		else{
			toReturn = toReturn + "-";
		}
		return toReturn;
	}
	
	public String DateToString(DateTime time){
		String result = "";
		result = result + time.getYear()+"-"+time.getMonthOfYear()+"-"+time.getDayOfMonth()+" "+time.getHourOfDay()+":"+time.getMinuteOfHour()+":"+time.getSecondOfMinute();
		return result;
	}
	
	@Test
	public void sucess(){ //listagem simples
		List<ListDirDto> dto;
		dto = new ArrayList<ListDirDto>();
		ListDirectoryService listdir = new ListDirectoryService(this.token);
		listdir.execute();
		//String [] result = listdir.getResult();
		String[] expected = new String[6];
		//Parent Dir
		expected[2] = "dir "+PermissionsToString(this.login.getDirectory().getParent().getPermissions());
		expected[2] = expected[2] + " 4 root " + Long.toString(this.login.getDirectory().getParent().getId())+ " ";
		expected[2] = expected[2] + DateToString(this.login.getDirectory().getParent().getLastModified()) + " ";
		expected[2] = expected[2] + "home";
		dto.add(new ListDirDto(expected[2]));
		//Dir itself
		expected[4] = "dir "+PermissionsToString(this.login.getDirectory().getPermissions());
		expected[4] = expected[4] + " 6 "+ this.login.getDirectory().getOwner().getUserName()+" " + Long.toString(this.login.getDirectory().getId())+ " ";
		expected[4] = expected[4] + DateToString(this.login.getDirectory().getLastModified()) + " ";
		expected[4] = expected[4] + "luissantos";
		dto.add(new ListDirDto(expected[4]));
		//dir created in workingDir
		expected[1] = "dir "+PermissionsToString(this.dir.getPermissions());
		expected[1] = expected[1] + " 2 luissantos " + Long.toString(this.dir.getId())+ " ";
		expected[1] = expected[1] + DateToString(this.dir.getLastModified()) + " ";
		expected[1] = expected[1] + "dir";
		dto.add(new ListDirDto(expected[1]));
		//plainFile created in working dir
		expected[5] = "PlainFile "+PermissionsToString(this.plain.getPermissions());
		expected[5] = expected[5] + " 8 luissantos " + Long.toString(this.plain.getId())+ " ";
		expected[5] = expected[5] + DateToString(this.plain.getLastModified()) + " ";
		expected[5] = expected[5] + "text";
		dto.add(new ListDirDto(expected[5]));
		//app created in working dir
		expected[0] = "app "+PermissionsToString(this.app.getPermissions());
		expected[0] = expected[0] + " 6 luissantos " + Long.toString(this.app.getId())+ " ";
		expected[0] = expected[0] + DateToString(this.app.getLastModified()) + " ";
		expected[0] = expected[0] + "app";
		dto.add(new ListDirDto(expected[0]));
		//link created in working dir
		expected[3] = "link "+PermissionsToString(this.link.getPermissions());
		expected[3] = expected[3] + " 13 luissantos " + Long.toString(this.link.getId())+ " ";
		expected[3] = expected[3] + DateToString(this.link.getLastModified()) + " ";
		expected[3] = expected[3] + "link";
		dto.add(new ListDirDto(expected[3]));
		Collections.sort(dto);
		List<ListDirDto> result = listdir.getDto();
		for(int x = 0; x < result.size(); x++){
			assertEquals("Did not list what was expected " + x, dto.get(x).getList(), result.get(x).getList());
		}
	}		
	@Test
	public void removeLinkAndList(){ //remove o link e lista
		 DeleteFileService service = new DeleteFileService(this.token, "link");
		 service.execute();
		 ListDirectoryService listdir = new ListDirectoryService(this.token);
		 listdir.execute();
		 String[] expected = new String[5];
		 //parent dir
		List<ListDirDto> dto;
		dto = new ArrayList<ListDirDto>();
		//Parent Dir
		expected[2] = "dir "+PermissionsToString(this.login.getDirectory().getParent().getPermissions());
		expected[2] = expected[2] + " 4 root " + Long.toString(this.login.getDirectory().getParent().getId())+ " ";
		expected[2] = expected[2] + DateToString(this.login.getDirectory().getParent().getLastModified()) + " ";
		expected[2] = expected[2] + "home";
		dto.add(new ListDirDto(expected[2]));
		//Dir itself
		expected[3] = "dir "+PermissionsToString(this.login.getDirectory().getPermissions());
		expected[3] = expected[3] + " 5 "+ this.login.getDirectory().getOwner().getUserName()+" " + Long.toString(this.login.getDirectory().getId())+ " ";
		expected[3] = expected[3] + DateToString(this.login.getDirectory().getLastModified()) + " ";
		expected[3] = expected[3] + "luissantos";
		dto.add(new ListDirDto(expected[3]));
		//dir created in workingDir
		expected[1] = "dir "+PermissionsToString(this.dir.getPermissions());
		expected[1] = expected[1] + " 2 luissantos " + Long.toString(this.dir.getId())+ " ";
		expected[1] = expected[1] + DateToString(this.dir.getLastModified()) + " ";
		expected[1] = expected[1] + "dir";
		dto.add(new ListDirDto(expected[1]));
		//plainFile created in working dir
		expected[4] = "PlainFile "+PermissionsToString(this.plain.getPermissions());
		expected[4] = expected[4] + " 8 luissantos " + Long.toString(this.plain.getId())+ " ";
		expected[4] = expected[4] + DateToString(this.plain.getLastModified()) + " ";
		expected[4] = expected[4] + "text";
		dto.add(new ListDirDto(expected[4]));
		//app created in working dir
		expected[0] = "app "+PermissionsToString(this.app.getPermissions());
		expected[0] = expected[0] + " 6 luissantos " + Long.toString(this.app.getId())+ " ";
		expected[0] = expected[0] + DateToString(this.app.getLastModified()) + " ";
		expected[0] = expected[0] + "app";
		dto.add(new ListDirDto(expected[0]));
		Collections.sort(dto);
		List<ListDirDto> result = listdir.getDto();
		for(int x = 0; x < result.size(); x++){
			assertEquals("2Did not list what was expected " + x, dto.get(x).getList(), result.get(x).getList());
		}
	}
	
	
	@Test
	public void changeDirAndList(){//ir para /home e listar
	
		ChangeDirectoryService service = new ChangeDirectoryService(this.token, "/home");
		service.execute();
		ListDirectoryService listdir = new ListDirectoryService(this.token);
		listdir.execute();
		String[] expected = new String[4];
		List<ListDirDto> dto;
		dto = new ArrayList<ListDirDto>();
		//Parent Dir
		expected[0] = "dir "+PermissionsToString(this.login.getDirectory().getParent().getPermissions());
		expected[0] = expected[0] + " 3 root " + Long.toString(this.login.getDirectory().getParent().getId())+ " ";
		expected[0] = expected[0] + DateToString(this.login.getDirectory().getParent().getLastModified()) + " ";
		expected[0] = expected[0] + "/";
		dto.add(new ListDirDto(expected[0]));
		//Dir itself
		expected[1] = "dir "+PermissionsToString(this.login.getDirectory().getPermissions());
		expected[1] = expected[1] + " 4 "+ this.login.getDirectory().getOwner().getUserName()+" " + Long.toString(this.login.getDirectory().getId())+ " ";
		expected[1] = expected[1] + DateToString(this.login.getDirectory().getLastModified()) + " ";
		expected[1] = expected[1] + "home";
		dto.add(new ListDirDto(expected[1]));
		//root homedir
		expected[3] = "dir "+PermissionsToString(this.login.getDirectory().getByName("root").getPermissions());
		expected[3] = expected[3] + " 2 root " + Long.toString(this.login.getDirectory().getByName("root").getId())+ " ";
		expected[3] = expected[3] + DateToString(this.login.getDirectory().getByName("root").getLastModified()) + " ";
		expected[3] = expected[3] + "root";
		dto.add(new ListDirDto(expected[3]));
		//luissantos homedir
		expected[2] = "dir "+PermissionsToString(this.login.getDirectory().getByName("luissantos").getPermissions());
		expected[2] = expected[2] + " 6 luissantos " + Long.toString(this.login.getDirectory().getByName("luissantos").getId())+ " ";
		expected[2] = expected[2] + DateToString(this.login.getDirectory().getByName("luissantos").getLastModified()) + " ";
		expected[2] = expected[2] + "luissantos";
		dto.add(new ListDirDto(expected[2]));
		Collections.sort(dto);
		List<ListDirDto> result = listdir.getDto();
		for(int x = 0; x < result.size(); x++){
			assertEquals("2Did not list what was expected " + x, dto.get(x).getList(), result.get(x).getList());
		}
	}
	
}