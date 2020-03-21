package exception;

public class duplicateKeyException extends MyException{
	public duplicateKeyException() { super(); }
	public duplicateKeyException(String message){ super(message); }
	public duplicateKeyException(String message, Throwable cause){
		super(message, cause);
		}
}