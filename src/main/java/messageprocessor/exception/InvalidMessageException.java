package messageprocessor.exception;

/**
 * Created by s.nathan on 19/07/2020.
 */
public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException(String s) {
        super(s);
    }
}