package dev.taskManager.backend.exception.exceptions;

public class RoleFormatException extends RuntimeException{
    public RoleFormatException() {
    }

    public RoleFormatException(String message) {
        super(message);
    }

    public RoleFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleFormatException(Throwable cause) {
        super(cause);
    }

    public RoleFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
