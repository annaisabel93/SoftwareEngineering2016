package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.DateTime;
import org.junit.Test;
import java.util.logging.Logger;

import pt.tecnico.mydrive.domain.App;
import pt.tecnico.mydrive.domain.Directory;
import pt.tecnico.mydrive.domain.Entity;
import pt.tecnico.mydrive.domain.FileSystem;
import pt.tecnico.mydrive.domain.Link;
import pt.tecnico.mydrive.domain.Login;
import pt.tecnico.mydrive.domain.PlainFile;
import pt.tecnico.mydrive.domain.User;
import pt.tecnico.mydrive.exception.AppDoesNotExistException;
import pt.tecnico.mydrive.exception.DirectoryDoesNotExistWithinDirectoryException;
import pt.tecnico.mydrive.exception.EntityDoesNotExistException;
import pt.tecnico.mydrive.exception.LinkDoesNotExistException;
import pt.tecnico.mydrive.exception.TexFileDoesNotExistException;


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
		User usr = new User(fs, "Luis", "luissantos", "luis", new byte[] {1,1,1,1} , "/home/luissantos");
		LoginService service = new LoginService("luissantos", "luis");
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
		
//		ListDirectoryService listdir = new ListDirectoryService(this.token);
//		listdir.execute();
//		//String [] result = listdir.getResult();
//		String[] expected = new String[6];
//		//Parent Dir
//		expected[0] = "dir "+PermissionsToString(this.login.getDirectory().getParent().getPermissions());
//		expected[0] = expected[0] + " 4 root" + Long.toString(this.login.getDirectory().getParent().getId())+ " ";
//		expected[0] = expected[0] + "home";
//		//Dir itself
//		expected[1] = "dir "+PermissionsToString(this.login.getDirectory().getPermissions());
//		expected[1] = expected[0] + " 6 "+ this.login.getDirectory().getOwner().getUserName()+" " + Long.toString(this.login.getDirectory().getId())+ " ";
//		expected[1] = expected[0] + "luis";
//		//dir created in workingDir
//		expected[2] = "dir "+PermissionsToString(this.dir.getPermissions());
//		expected[2] = expected[0] + " 2 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		expected[2] = expected[0] + "dir";
//		//plainFile created in working dir
//		expected[3] = "plainFile "+PermissionsToString(this.plain.getPermissions());
//		expected[3] = expected[0] + " 0 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		expected[3] = expected[0] + "text";
//		//app created in working dir
//		expected[4] = "app "+PermissionsToString(this.app.getPermissions());
//		expected[4] = expected[0] + " 0 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		expected[4] = expected[0] + "app";
//		//link created in working dir
//		expected[5] = "link "+PermissionsToString(this.link.getPermissions());
//		expected[5] = expected[0] + " 0 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		expected[5] = expected[0] + "link";
//		
//		assertEquals("Did not list what was expected", expected, result);      
	}		
	@Test
	public void removeLinkAndList(){ //remove o link e lista
//		 DeleteFileService service = new DeleteFileService(this.token, "link");
//		 service.execute();
//		 //String [] result = listdir.getResult();
//		 String[] expected = new String[6];
//		 //parent dir
//		 expected[0] = "dir "+PermissionsToString(this.login.getDirectory().getParent().getPermissions());
//		 expected[0] = expected[0] + " 4 root" + Long.toString(this.login.getDirectory().getParent().getId())+ " ";
//		 expected[0] = expected[0] + "home";
//		 //dir itself
//		 expected[1] = "dir "+PermissionsToString(this.login.getDirectory().getPermissions());
//		 expected[1] = expected[0] + " 6 "+ this.login.getDirectory().getOwner().getUserName()+" " + Long.toString(this.login.getDirectory().getId())+ " ";
//		 expected[1] = expected[0] + "luis";
//		 //dir in workingDir
//		 expected[2] = "dir "+PermissionsToString(this.dir.getPermissions());
//		 expected[2] = expected[0] + " 2 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		 expected[2] = expected[0] + "dir";
//		 //PlainFile in workingDir
//		 expected[3] = "plainFile "+PermissionsToString(this.plain.getPermissions());
//		 expected[3] = expected[0] + " 0 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		 expected[3] = expected[0] + "text";
//		 //App in working dir
//		 expected[4] = "app "+PermissionsToString(this.app.getPermissions());
//		 expected[4] = expected[0] + " 0 luis" + Long.toString(this.login.getDirectory().getId())+ " ";
//		 expected[4] = expected[0] + "app";
//		 
//		 assertEquals("Did not list what was expected", expected, result); 
	}
}