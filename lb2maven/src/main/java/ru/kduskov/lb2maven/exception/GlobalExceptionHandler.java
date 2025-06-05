package ru.kduskov.lb2maven.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kduskov.lb2maven.dto.response.BaseResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());
        var details = "Validation error: " + ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(new BaseResponse(false, details));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());
        var details = "Constraint violation: " + ex.getMessage();
        return ResponseEntity.badRequest().body(new BaseResponse(false, details));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleRuntime(RuntimeException ex) {
        log.warn("Runtime exception: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(false, ex.getMessage()));
    }
}
