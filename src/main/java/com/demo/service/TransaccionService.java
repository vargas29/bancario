package com.demo.service;

import com.demo.dto.TransaccionDTO;
import com.demo.entity.Cuenta;
import com.demo.entity.Transaccion;
import com.demo.excepciones.CuentaNoEncontradaException;
import com.demo.excepciones.cuenta.SaldoInsuficienteException;
import com.demo.repository.CuentaRepository;
import com.demo.repository.TransaccionRepository;
import com.demo.util.TipoTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;

@Service
public class TransaccionService {
    private static final Logger logger = LogManager.getLogger(TransaccionService.class);


    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public TransaccionDTO realizarTransaccion(TransaccionDTO transaccionDTO) {

        logger.info("Realizando transacción desde cuenta {} a cuenta {}", transaccionDTO.getCuentaOrigenId(), transaccionDTO.getCuentaDestinoId());
        // Resto del código
        Cuenta cuentaOrigen = obtenerCuentaPorId(transaccionDTO.getCuentaOrigenId());
        Cuenta cuentaDestino = obtenerCuentaPorId(transaccionDTO.getCuentaDestinoId());

        validarSaldoSuficiente(cuentaOrigen, transaccionDTO);

        realizarOperacion(transaccionDTO, cuentaOrigen, cuentaDestino);

        return transaccionDTO;
    }

    private Cuenta obtenerCuentaPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));
    }

    private void validarSaldoSuficiente(Cuenta cuenta, TransaccionDTO transaccionDTO) {
        if (transaccionDTO.getTipo() == TipoTransaccion.RETIRO || transaccionDTO.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            if (cuenta.getSaldo().compareTo(BigDecimal.valueOf(transaccionDTO.getMonto())) < 0) {

                logger.error("Saldo insuficiente para la transacción en la cuenta {}", cuenta.getId());
                throw new SaldoInsuficienteException("Saldo insuficiente para la transacción");
            }

        }
    }

    private void realizarOperacion(TransaccionDTO transaccionDTO, Cuenta cuentaOrigen, Cuenta cuentaDestino) {

        logger.error("Realizar Operacion {}");

        Transaccion transaccion = new Transaccion();
        // setear los valores de transaccionDTO a transaccion
        transaccionRepository.save(transaccion);

        if (transaccionDTO.getTipo() == TipoTransaccion.RETIRO || transaccionDTO.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(BigDecimal.valueOf(transaccionDTO.getMonto())));

        }

        if (transaccionDTO.getTipo() == TipoTransaccion.CONSIGNACION || transaccionDTO.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(BigDecimal.valueOf(transaccionDTO.getMonto())));

        }

        cuentaRepository.save(cuentaOrigen);
        cuentaRepository.save(cuentaDestino);
    }
}
