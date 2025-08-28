package com.fredodev.riee.appointment.domain.exception;

public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Duplicate appointment found");
    }

    public DuplicateAppointmentException(String message) {
        super(message);
    }
}
