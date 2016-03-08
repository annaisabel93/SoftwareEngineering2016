package pt.tecnico.myDrive.domain;

public class User extends User_Base {
    
    public User(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir, boolean isRoot) {
        super();
        setName(name);
        setUserName(username);
        setPassword(password);
        setMask(mask);
        setHomeDir(homeDir);
        setIsRoot(isRoot);
        setSystem(filesystem);
    }
    
    @Override
    public void setSystem(FileSystem fs){
    	if(fs == null)
    		super.setSystem(null);
    	else{
    		fs.addUser(this);
    	}
    }
}
