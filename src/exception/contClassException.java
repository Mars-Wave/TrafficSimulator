package exception;

public class contClassException extends MyException{
	public contClassException() { super(); }
	public contClassException(String message){ super(message); }
	public contClassException(String message, Throwable cause){
		super(message, cause);
	}
}
