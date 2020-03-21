package exception;

public class negativeValue extends MyException{
	public negativeValue() { super(); }
	public negativeValue(String message){ super(message); }
	public negativeValue(String message, Throwable cause){
		super(message, cause);
		}

}