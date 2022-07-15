package dev.taskManager.backend.errorHandlers.exceptions;

public class StatusAlreadyExistException extends RuntimeException{
    public StatusAlreadyExistException() {
    }

    public StatusAlreadyExistException(String message) {
        super(message);
    }

    public StatusAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatusAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public StatusAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
