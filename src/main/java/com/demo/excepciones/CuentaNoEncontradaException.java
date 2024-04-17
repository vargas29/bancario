package com.demo.excepciones;

// Excepciones personalizadas
public class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(String message) {
        super(message);
    }
}
