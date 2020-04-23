package exceptions;

/**
 * Исключение, означающее несоответствие каких-либо полей элемента коллекции условиям
 */
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String message){
        super(message);
    }
}
