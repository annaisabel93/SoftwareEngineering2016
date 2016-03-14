package pt.tecnico.myDrive.exception;

public class NotLoggedOnException extends MyDriveException {
		
		private static final long serialVersionUID = 1L;
		

		public NotLoggedOnException() {
		}

		@Override
		public String getMessage() {
			return "You must loggin first.";
		}

}
