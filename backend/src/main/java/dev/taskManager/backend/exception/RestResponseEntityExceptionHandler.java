package dev.taskManager.backend.exception;

import dev.taskManager.backend.exception.exceptions.ResourceAlreadyExistException;
import dev.taskManager.backend.exception.exceptions.ResourceNotFoundException;
import dev.taskManager.backend.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        var responseBody = constructResponse(exception, NOT_FOUND);
        return ResponseEntity.status(NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExistExceptionHandler(ResourceAlreadyExistException exception){
        var responseBody = constructResponse(exception, CONFLICT);
        return ResponseEntity.status(CONFLICT).body(responseBody);
    }

    private ErrorResponse constructResponse(RuntimeException exception, HttpStatus status){
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .success(false)
                .status(status)
                .statusCode(status.value())
                .message(exception.getMessage())
                .build();
    }

}

