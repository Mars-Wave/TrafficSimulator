
package exception;

public class vehicleCoherenceException  extends MyException{
    public vehicleCoherenceException() { super(); }
    public vehicleCoherenceException(String message){ super(message); }
    public vehicleCoherenceException(String message, Throwable cause){
        super(message, cause);
    }
}
