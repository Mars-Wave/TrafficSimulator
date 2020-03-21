package exception;

public class roadMapCoherenceException extends MyException{
    public roadMapCoherenceException() { super(); }
    public roadMapCoherenceException(String message){ super(message); }
    public roadMapCoherenceException(String message, Throwable cause){
        super(message, cause);
    }
}