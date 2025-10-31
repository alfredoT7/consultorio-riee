package com.fredodev.riee.dentist.domain.exception;

public class DuplicateDEntistException extends RuntimeException{
    public DuplicateDEntistException(String nombreDentista) {
        super("Ya existe un dentista con el nombre: " + nombreDentista);
    }
}
