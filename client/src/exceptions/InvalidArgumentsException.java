package exceptions;

/**
 * Исключение, обозначающее неверные аргументы команды
 */
public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(String message){
        super(message);
    }
}
