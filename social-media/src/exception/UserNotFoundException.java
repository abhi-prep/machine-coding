package exception;

// --- Custom Exceptions ---
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super("User not found: " + msg);
    }
}
