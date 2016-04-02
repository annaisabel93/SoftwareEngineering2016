package pt.tecnico.mydrive.domain;

public class Root extends Root_Base {
    
    public Root(FileSystem filesystem, String name, String username, String password, byte[] mask, String homeDir) {
        super();
        setName(name);
        setUserName(username);
        setPassword(password);
        setMask(mask);
        setHomeDir(homeDir);
        setSystem(filesystem);
    }
    
}
