package com.fredodev.riee.treatment.domain.exception;

public class TreatmentHistoryNotFoundException extends RuntimeException {
    public TreatmentHistoryNotFoundException(Long id) {
        super("Historial de tratamiento con ID " + id + " no encontrado");
    }
}
