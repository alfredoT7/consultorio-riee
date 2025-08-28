package com.fredodev.riee.appointment.domain.exception;

public class InvalidAppointmentException extends RuntimeException {
    public InvalidAppointmentException(String message) {
        super(message);
    }
}

