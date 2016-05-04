package pt.tecnico.mydrive.presentation;

public class MyDriveShell extends Shell {

  public static void main(String[] args) throws Exception {
    MyDriveShell sh = new MyDriveShell();
    sh.execute();
  }

  public MyDriveShell() { // Adicionem aqui os comandos correspondentes a cada coisa da apresentacao
    super("MyDrive");
    //new CreatePerson(this);
    //new CreateContact(this);
    //new RemovePerson(this);
    //new RemoveContact(this);
    //new List(this);
    //new Import(this);
    //new Export(this);
  }
}