package com.demo.excepciones.cliente;

public class NombreApellidoInvalidoException extends RuntimeException {
    public NombreApellidoInvalidoException(String message) {
        super(message);
    }
}
