package exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String msg) {
        super("Post not found: " + msg);
    }
}
