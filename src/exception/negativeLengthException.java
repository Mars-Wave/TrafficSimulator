package exception;

public class negativeLengthException extends MyException{
	public negativeLengthException() { super(); }
	public negativeLengthException(String message){ super(message); }
	public negativeLengthException(String message, Throwable cause){
		super(message, cause);
		}

}