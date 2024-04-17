package com.demo.service;

import com.demo.dto.TransaccionDTO;
import com.demo.entity.Cuenta;
import com.demo.excepciones.CuentaNoEncontradaException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.repository.CuentaRepository;
import com.demo.repository.TransaccionRepository;
import com.demo.service.TransaccionService;
import com.demo.util.TipoTransaccion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private TransaccionService transaccionService;

    @Test
    public void realizarTransaccion_Transferencia_SaldoSuficiente() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();

        transaccionDTO.setTipo(TipoTransaccion.TRANSFERENCIA);
        transaccionDTO.setId(2L);
        transaccionDTO.setCuentaDestinoId(3L);
        transaccionDTO.setCuentaOrigenId(2L);
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setSaldo(BigDecimal.valueOf(200));
        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setSaldo(BigDecimal.valueOf(300));
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepository.findById(2L)).thenReturn(Optional.of(cuentaDestino));

        // Act
        TransaccionDTO resultado = transaccionService.realizarTransaccion(transaccionDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(TipoTransaccion.TRANSFERENCIA, resultado.getTipo());
        // Agrega más aserciones según sea necesario

    }

    @Test
    public void realizarTransaccion_Transferencia_SaldoInsuficiente() {
        // Arrange
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setSaldo(BigDecimal.valueOf(200));
        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setSaldo(BigDecimal.valueOf(300));
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepository.findById(2L)).thenReturn(Optional.of(cuentaDestino));

        // Act & Assert
        assertThrows(SaldoInsuficienteException.class, () -> transaccionService.realizarTransaccion(transaccionDTO));
        verify(cuentaRepository, never()).save(any());
    }

    // Agrega más pruebas para otros tipos de transacciones y casos según sea necesario
}
