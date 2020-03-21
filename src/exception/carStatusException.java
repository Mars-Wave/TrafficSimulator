package exception;

public class carStatusException extends MyException{
	public carStatusException() { super(); }
	public carStatusException(String message){ super(message); }
	public carStatusException(String message, Throwable cause){
		super(message, cause);
		}
}