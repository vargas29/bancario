package com.demo.excepciones.usuario;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}