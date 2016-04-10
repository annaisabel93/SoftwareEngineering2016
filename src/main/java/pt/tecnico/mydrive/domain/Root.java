package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.RootCannotBeRemovedException;

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
    
    public void remove() throws RootCannotBeRemovedException{
    		throw new RootCannotBeRemovedException();
    }
    
}
