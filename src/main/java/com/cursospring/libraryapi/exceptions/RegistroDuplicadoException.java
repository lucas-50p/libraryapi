package com.cursospring.libraryapi.exceptions;

// Regra de negocio
public class RegistroDuplicadoException extends RuntimeException{

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
