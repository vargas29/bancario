package com.demo.excepciones.cliente;

public class CorreoElectronicoInvalidoException extends RuntimeException {
    public CorreoElectronicoInvalidoException(String message) {
        super(message);
    }
}
