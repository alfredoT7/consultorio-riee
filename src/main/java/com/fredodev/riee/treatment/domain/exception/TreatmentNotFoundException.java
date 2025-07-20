package com.fredodev.riee.treatment.domain.exception;

public class TreatmentNotFoundException extends RuntimeException {
    public TreatmentNotFoundException(Long id) {
        super("Tratamiento con ID " + id + " no encontrado");
    }
}
