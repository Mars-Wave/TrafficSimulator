package exception;

public class unexistingReferencedObject  extends MyException{
	public unexistingReferencedObject() { super(); }
	public unexistingReferencedObject(String message){ super(message); }
	public unexistingReferencedObject(String message, Throwable cause){
		super(message, cause);
		}
}
