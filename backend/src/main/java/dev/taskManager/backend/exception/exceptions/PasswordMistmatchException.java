package dev.taskManager.backend.exception.exceptions;

public class PasswordMistmatchException extends RuntimeException{
    public PasswordMistmatchException() {
    }

    public PasswordMistmatchException(String message) {
        super(message);
    }

    public PasswordMistmatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMistmatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMistmatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
