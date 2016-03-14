package pt.tecnico.myDrive.exception;

public class NotLoggedOnException extends MyDriveException {
		
		private static final long serialVersionUID = 1L;
		
		private String username;

		public NotLoggedOnException(String username) {
			this.username = username;
		}


		//e suposto apanhar se ele nao estiver logado
		public String getLoggedUSer(){
			return username;
		}

		@Override
		public String getMessage() {
			return "You must loggin first.";
		}

}
