package com.fredodev.riee.appointment.infrastructure.exception;

import com.fredodev.riee.appointment.domain.exception.AppointmentNotFoundException;
import com.fredodev.riee.appointment.domain.exception.DuplicateAppointmentException;
import com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException;
import com.fredodev.riee.appointment.infrastructure.rest.AppointmentAvailabilityController;
import com.fredodev.riee.appointment.infrastructure.rest.AppointmentController;
import com.fredodev.riee.appointment.infrastructure.rest.AppointmentStatusController;
import com.fredodev.riee.appointment.infrastructure.rest.AppointmentWorkflowController;
import com.fredodev.riee.shared.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice(assignableTypes = {
        AppointmentController.class,
        AppointmentWorkflowController.class,
        AppointmentAvailabilityController.class,
        AppointmentStatusController.class
})
public class AppointmentExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        HttpStatus.NOT_FOUND.value(),
                        "Recurso de cita no encontrado",
                        List.of(ex.getMessage())
                ));
    }

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateAppointmentException(DuplicateAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        "Conflicto de cita",
                        List.of(ex.getMessage())
                ));
    }

    @ExceptionHandler(InvalidAppointmentException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAppointmentException(InvalidAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "Datos de cita invalidos",
                        List.of(ex.getMessage())
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocurrio un error durante la operacion de citas",
                        List.of(ex.getMessage())
                ));
    }
}
