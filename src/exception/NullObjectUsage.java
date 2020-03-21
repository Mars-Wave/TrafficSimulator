package exception;

public class NullObjectUsage  extends MyException{
    public NullObjectUsage() { super(); }
    public NullObjectUsage(String message){ super(message); }
    public NullObjectUsage(String message, Throwable cause){
        super(message, cause);
    }
}
