package exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productName, int available, int requested) {
        super("❌ Not enough stock for " + productName + ". Available: " + available + ", Requested: " + requested);
    }
}
