package src.exceptions;

public class MissmatchDataException extends Exception {

    /**
     * @param error throws given error
     */
    public MissmatchDataException(String error) {
        super(error);
    }
    
}
