package com.fredodev.riee.treatment.domain.exception;

public class DuplicateTreatmentException extends RuntimeException {
    public DuplicateTreatmentException(String nombreTratamiento) {
        super("Ya existe un tratamiento con el nombre: " + nombreTratamiento);
    }
}
