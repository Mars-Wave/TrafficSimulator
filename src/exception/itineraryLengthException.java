package exception;

public class itineraryLengthException extends MyException{
	public itineraryLengthException() { super(); }
	public itineraryLengthException(String message){ super(message); }
	public itineraryLengthException(String message, Throwable cause){
		super(message, cause);
		}
}
