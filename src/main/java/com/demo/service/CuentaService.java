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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CuentaService {

    private static final Logger logger = LogManager.getLogger(CuentaService.class);

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Cuenta crearCuenta(String tipoCuenta, String estado, BigDecimal saldo, Boolean exentaGMF, Long clienteId) {

        logger.info("Creando cuenta para el cliente con ID: {}", clienteId);
        // Validar el tipo de cuenta
        if (!tipoCuenta.equals("cuenta corriente") && !tipoCuenta.equals("cuenta de ahorros")) {
            throw new TipoCuentaInvalidoException("Tipo de cuenta inválido");
        }

        // Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Generar número de cuenta
        String numeroCuenta = generarNumeroCuenta(tipoCuenta);

        // Crear la cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setEstado(estado);
        cuenta.setSaldo(saldo);
        cuenta.setExentaGMF(exentaGMF);
        cuenta.setFechaCreacion(new Date());
        cuenta.setCliente(cliente);

        // Validar el saldo de la cuenta de ahorros
        if (tipoCuenta.equals("cuenta de ahorros") && saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("El saldo de la cuenta de ahorros no puede ser menor a $0");
        }

        return cuentaRepository.save(cuenta);
    }

    private String generarNumeroCuenta(String tipoCuenta) {
        String prefix = tipoCuenta.equals("cuenta corriente") ? "33" : "53";
        String numeroCuenta = prefix + new Random().nextInt(100000000);
        return numeroCuenta;
    }

    @Transactional
    public void transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto) {

        logger.info("Transferencia de {} desde la cuenta {} a la cuenta {}", monto, cuentaOrigenId, cuentaDestinoId);

        Cuenta cuentaOrigen = cuentaRepository.findById(cuentaOrigenId).orElseThrow(() -> new CuentaNotFoundException("Cuenta origen no encontrada"));
        Cuenta cuentaDestino = cuentaRepository.findById(cuentaDestinoId).orElseThrow(() -> new CuentaNotFoundException("Cuenta destino no encontrada"));

        // Validar el saldo antes de realizar la transferencia
        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta origen");
        }

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(monto));
        cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(monto));

        cuentaRepository.save(cuentaOrigen);
        cuentaRepository.save(cuentaDestino);
    }

    @Transactional
    public Cuenta consultarCuenta(Long cuentaId) {

        logger.info("Consultando información de la cuenta con ID: {}", cuentaId);
        // Validar que la cuenta exista
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));
        return cuenta;
    }

    @Transactional
    public void eliminarCuenta(Long cuentaId) {

        logger.info("Eliminando cuenta con ID: {}", cuentaId);
        // Validar que la cuenta exista
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        // Validar que la cuenta no tenga saldo
        if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new CuentaConSaldoException("La cuenta tiene saldo. No se puede eliminar");
        }

        cuentaRepository.delete(cuenta);
    }
    @Transactional
    public List<Cuenta> listarCuentasPorNumeroIdentificacionCliente(String numeroIdentificacionCliente) {

        logger.info("Listando cuentas del cliente con número de identificación: {}", numeroIdentificacionCliente);
        // Validar que el cliente exista
        Cliente cliente = clienteRepository.findByNumeroIdentificacion(numeroIdentificacionCliente)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Obtener todas las cuentas asociadas al cliente
        List<Cuenta> cuentas = cuentaRepository.findByCliente(cliente);

        if (cuentas.isEmpty()) {
            throw new CuentaNotFoundException("El cliente no tiene cuentas asociadas");
        }

        return cuentas;
    }



}