package com.demo.excepciones.cuenta;

public class CuentaConSaldoException extends RuntimeException {
    public CuentaConSaldoException(String mensaje) {
        super(mensaje);
    }
}
