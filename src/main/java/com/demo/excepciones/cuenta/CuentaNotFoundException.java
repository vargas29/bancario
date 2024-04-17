package com.demo.excepciones.cuenta;

public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
