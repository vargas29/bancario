package com.demo.adaptadores;

import com.demo.excepciones.cuenta.CuentaConSaldoException;
import com.demo.excepciones.cuenta.CuentaNotFoundException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.excepciones.cuenta.TipoCuentaInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.demo.excepciones.cliente.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteMenorEdadException.class)
    public ResponseEntity<?> handleClienteMenorEdadException(ClienteMenorEdadException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NombreApellidoInvalidoException.class)
    public ResponseEntity<?> handleNombreApellidoInvalidoException(NombreApellidoInvalidoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CorreoElectronicoInvalidoException.class)
    public ResponseEntity<?> handleCorreoElectronicoInvalidoException(CorreoElectronicoInvalidoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<?> handleClienteNotFoundException(ClienteNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    // Manejo de excepciones relacionadas con cuentas
    @ExceptionHandler(CuentaConSaldoException.class)
    public ResponseEntity<?> handleCuentaConSaldoException(CuentaConSaldoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CuentaNotFoundException.class)
    public ResponseEntity<?> handleCuentaNotFoundException(CuentaNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> handleSaldoInsuficienteException(SaldoInsuficienteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TipoCuentaInvalidoException.class)
    public ResponseEntity<?> handleTipoCuentaInvalidoException(TipoCuentaInvalidoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
