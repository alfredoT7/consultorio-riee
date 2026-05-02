package com.fredodev.riee.treatment.domain.exception;

public class DentistNotAssignedException extends RuntimeException {
    public DentistNotAssignedException(Long treatmentHistoryId) {
        super("No hay dentista principal asignado al historial " + treatmentHistoryId);
    }
}
