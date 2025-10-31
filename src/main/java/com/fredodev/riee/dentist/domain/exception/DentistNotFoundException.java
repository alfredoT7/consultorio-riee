package com.fredodev.riee.dentist.domain.exception;

public class DentistNotFoundException extends RuntimeException {
    public DentistNotFoundException(Long id) {
        super("Dentist with ID " + id + " not found");
    }
}
