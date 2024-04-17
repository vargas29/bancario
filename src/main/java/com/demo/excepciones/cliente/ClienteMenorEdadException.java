package com.demo.excepciones.cliente;

public class ClienteMenorEdadException extends RuntimeException {
    public ClienteMenorEdadException(String message) {
        super(message);
    }
}
