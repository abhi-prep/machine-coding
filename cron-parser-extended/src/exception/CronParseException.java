package exception;

/**
 * Exception thrown when a cron expression cannot be parsed correctly.
 */
public class CronParseException extends RuntimeException {
    public CronParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CronParseException(String message) {
        super(message);
    }
}
