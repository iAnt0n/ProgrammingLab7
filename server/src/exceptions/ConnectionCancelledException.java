package exceptions;

/**
 * Исключение, означающее разрыв соединения не через exit
 */
public class ConnectionCancelledException extends RuntimeException{
    public ConnectionCancelledException(String message){
        super(message);
    }
}
