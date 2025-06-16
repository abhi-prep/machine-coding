package exception;

public class RideAlreadyExistsException extends RuntimeException {
    public RideAlreadyExistsException(String msg) {
        super(msg);
    }
}
