package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

public class Directory extends Directory_Base {
    
    public Directory(User user, String filename, String owner, long id, DateTime lastModified, int dimension, boolean read, boolean write, boolean delete, boolean execute) {
        super();
        setUser(user);
    }
}
