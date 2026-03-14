package com.fredodev.riee.cloudinary.infrastructure.exception;

import com.fredodev.riee.cloudinary.domain.exception.CloudinaryOperationException;
import com.fredodev.riee.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CloudinaryExceptionHandler {

    @ExceptionHandler(CloudinaryOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleCloudinaryOperationException(CloudinaryOperationException exception) {
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Operacion con Cloudinary fallida",
                List.of(exception.getMessage())
        );
        return ResponseEntity.badRequest().body(response);
    }
}
