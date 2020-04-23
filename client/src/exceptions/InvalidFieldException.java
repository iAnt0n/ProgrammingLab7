package exceptions;

/**
 * Исключение, означающее несоответствие каких-либо полей элемента коллекции условиям
 */
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String mesage){
        super(mesage);
    }
}
