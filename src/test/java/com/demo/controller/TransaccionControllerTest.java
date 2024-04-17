package com.demo.controller;

import com.demo.dto.TransaccionDTO;
import com.demo.excepciones.CuentaNoEncontradaException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.service.TransaccionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    @Test
    public void realizarTransaccion_ValidDTO_ReturnsOKStatus() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        TransaccionDTO resultadoEsperado = new TransaccionDTO();
        when(transaccionService.realizarTransaccion(any())).thenReturn(resultadoEsperado);

        // Act
        ResponseEntity<TransaccionDTO> response = transaccionController.realizarTransaccion(transaccionDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultadoEsperado, response.getBody());
    }

    @Test
    public void realizarTransaccion_CuentaNoEncontrada_ReturnsNotFoundStatus() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        when(transaccionService.realizarTransaccion(any())).thenThrow(CuentaNoEncontradaException.class);

        // Act
        ResponseEntity<TransaccionDTO> response = transaccionController.realizarTransaccion(transaccionDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void realizarTransaccion_SaldoInsuficiente_ReturnsBadRequestStatus() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        when(transaccionService.realizarTransaccion(any())).thenThrow(SaldoInsuficienteException.class);

        // Act
        ResponseEntity<TransaccionDTO> response = transaccionController.realizarTransaccion(transaccionDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void realizarTransaccion_InternalServerError_ReturnsInternalServerErrorStatus() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        when(transaccionService.realizarTransaccion(any())).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<TransaccionDTO> response = transaccionController.realizarTransaccion(transaccionDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
