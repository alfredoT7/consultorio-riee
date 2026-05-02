package com.fredodev.riee.treatment.infrastructure.exception;

import com.fredodev.riee.shared.api.ApiResponse;
import com.fredodev.riee.treatment.domain.exception.DentistNotAssignedException;
import com.fredodev.riee.treatment.domain.exception.DuplicateTreatmentException;
import com.fredodev.riee.treatment.domain.exception.InvalidPaymentAmountException;
import com.fredodev.riee.treatment.domain.exception.InvalidTreatmentStatusException;
import com.fredodev.riee.treatment.domain.exception.TreatmentHistoryNotFoundException;
import com.fredodev.riee.treatment.domain.exception.TreatmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TreatmentExceptionHandler {

    @ExceptionHandler(TreatmentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTreatmentNotFoundException(TreatmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage(), List.of(ex.getMessage())));
    }

    @ExceptionHandler(DuplicateTreatmentException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateTreatmentException(DuplicateTreatmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(409, ex.getMessage(), List.of(ex.getMessage())));
    }

    @ExceptionHandler(TreatmentHistoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTreatmentHistoryNotFoundException(TreatmentHistoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage(), List.of(ex.getMessage())));
    }

    @ExceptionHandler(InvalidPaymentAmountException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPaymentAmountException(InvalidPaymentAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, ex.getMessage(), List.of(ex.getMessage())));
    }

    @ExceptionHandler(InvalidTreatmentStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidTreatmentStatusException(InvalidTreatmentStatusException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage(), List.of(ex.getMessage())));
    }

    @ExceptionHandler(DentistNotAssignedException.class)
    public ResponseEntity<ApiResponse<Void>> handleDentistNotAssignedException(DentistNotAssignedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage(), List.of(ex.getMessage())));
    }
}
