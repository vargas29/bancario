package com.demo.excepciones.cuenta;

public class TipoCuentaInvalidoException extends RuntimeException {
    public TipoCuentaInvalidoException(String mensaje) {
        super(mensaje);
    }
}