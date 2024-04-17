package com.demo.service;

import com.demo.entity.Cliente;
import com.demo.entity.Cuenta;
import com.demo.excepciones.cliente.ClienteNotFoundException;
import com.demo.excepciones.cuenta.CuentaConSaldoException;
import com.demo.excepciones.cuenta.CuentaNotFoundException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.excepciones.cuenta.TipoCuentaInvalidoException;
import com.demo.repository.ClienteRepository;
import com.demo.repository.CuentaRepository;
import com.demo.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @Test
    public void crearCuenta_Valida() {
        // Arrange
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cuenta cuenta = cuentaService.crearCuenta("cuenta corriente", "activo", BigDecimal.valueOf(1000), false, 1L);

        // Assert
        assertNotNull(cuenta);
        assertEquals("cuenta corriente", cuenta.getTipoCuenta());
        // Agrega más aserciones según sea necesario
    }

    @Test
    public void crearCuenta_TipoInvalido() {
        // Arrange

        // Act & Assert
        assertThrows(TipoCuentaInvalidoException.class, () -> cuentaService.crearCuenta("cuenta invalida", "activo", BigDecimal.valueOf(1000), false, 1L));
    }

    // Agrega más pruebas para otros casos según sea necesario

    @Test
    public void eliminarCuenta_ConSaldo() {
        // Arrange
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(BigDecimal.valueOf(1000));
        when(cuentaRepository.findById(anyLong())).thenReturn(Optional.of(cuenta));

        // Act & Assert
        assertThrows(CuentaConSaldoException.class, () -> cuentaService.eliminarCuenta(1L));
    }

    // Agrega más pruebas para otros métodos según sea necesario
}
