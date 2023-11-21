public class DBAppException extends Exception {
	String message;
	public DBAppException(String message){
		this.message = message;
	}
}
