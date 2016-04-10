package pt.tecnico.mydrive.domain;

public class Variable extends Variable_Base {
    
    public Variable(Login login, String name,  String value) {
        super();
        setLogin(login);
        setName(name);
        setValue(value);
    }
    
}
