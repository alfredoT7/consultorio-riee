package com.fredodev.riee.treatment.domain.exception;

public class InvalidTreatmentStatusException extends RuntimeException {
    public InvalidTreatmentStatusException(Long statusId) {
        super("Estado de tratamiento con ID " + statusId + " no existe");
    }
}
