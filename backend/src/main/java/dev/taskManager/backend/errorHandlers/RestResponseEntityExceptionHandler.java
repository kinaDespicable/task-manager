package dev.taskManager.backend.errorHandlers;

import dev.taskManager.backend.errorHandlers.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorMessage> roleDoesNotExistException(RoleNotFoundException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> roleAlreadyExistException(RoleAlreadyExistException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<ErrorMessage> statusDoesNotExistException(StatusNotFoundException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(StatusAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> statusAlreadyExistException(StatusAlreadyExistException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorMessage> passwordMismatchException(PasswordMismatchException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistException(UserAlreadyExistException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}