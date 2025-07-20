package com.fredodev.riee.treatment.infrastructure.exception;

import com.fredodev.riee.treatment.domain.exception.DuplicateTreatmentException;
import com.fredodev.riee.treatment.domain.exception.TreatmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TreatmentExceptionHandler {

    @ExceptionHandler(TreatmentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTreatmentNotFoundException(TreatmentNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateTreatmentException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateTreatmentException(DuplicateTreatmentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}
