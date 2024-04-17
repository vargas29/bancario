package com.demo.controller;

import com.demo.dto.CuentaDTO;
import com.demo.entity.Cuenta;
import com.demo.excepciones.cuenta.CuentaNotFoundException;
import com.demo.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @Test
    public void crearCuenta_ValidDTO_ReturnsCreatedStatus() {
        // Arrange
        CuentaDTO cuentaDTO = new CuentaDTO();
        Cuenta cuenta = new Cuenta();
        when(cuentaService.crearCuenta(anyString(), anyString(), any(), anyBoolean(), anyLong())).thenReturn(cuenta);

        // Act
        ResponseEntity<Cuenta> response = cuentaController.crearCuenta(cuentaDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    public void transferir_ValidIdsAndAmount_ReturnsOKStatus() {
        // Arrange
        Long cuentaOrigenId = 1L;
        Long cuentaDestinoId = 2L;
        BigDecimal monto = BigDecimal.valueOf(100);

        ResponseEntity<Void> response = cuentaController.transferir(cuentaOrigenId, cuentaDestinoId, monto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void consultarCuenta_ExistingId_ReturnsOKStatus() {
        // Arrange
        Long cuentaId = 1L;
        Cuenta cuenta = new Cuenta();
        when(cuentaService.consultarCuenta(anyLong())).thenReturn(cuenta);

        // Act
        ResponseEntity<Cuenta> response = cuentaController.consultarCuenta(cuentaId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    public void eliminarCuenta_ExistingId_ReturnsOKStatus() {
        // Arrange
        Long cuentaId = 1L;
        // No necesitamos simular el servicio aquí ya que la prueba se enfoca en el controlador
        // La simulación del servicio se haría en pruebas unitarias separadas para CuentaService
        // Cuando se prueba el servicio en sí mismo
        // En este caso, solo queremos asegurarnos de que el controlador maneje correctamente las respuestas

        // Act
        ResponseEntity<Void> response = cuentaController.eliminarCuenta(cuentaId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Agrega más pruebas para otros métodos del controlador según sea necesario
}
