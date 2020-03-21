package exception;

public class maxSpeedException extends MyException{
	public maxSpeedException() { super(); }
	public maxSpeedException(String message){ super(message); }
	public maxSpeedException(String message, Throwable cause){
		super(message, cause);
		}
}
