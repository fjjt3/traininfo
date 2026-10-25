package com.traininfo.interfaces.rest;

import com.traininfo.domain.exception.InvalidJourneyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationFailure(MethodArgumentNotValidException exception) {
        List<String> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": "
                        + Objects.requireNonNullElse(error.getDefaultMessage(), "is invalid"))
                .toList();
        ProblemDetail problem = badRequest("Request validation failed.");
        problem.setProperty("errors", violations);
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(InvalidJourneyException.class)
    public ResponseEntity<ProblemDetail> handleInvalidJourney(InvalidJourneyException exception) {
        return ResponseEntity.badRequest().body(badRequest(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(
                badRequest("Request body is invalid or contains an unsupported value format. Dates must use ISO-8601.")
        );
    }

    private ProblemDetail badRequest(String detail) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    }
}
