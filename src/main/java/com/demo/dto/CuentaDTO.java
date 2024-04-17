package com.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CuentaDTO {
    private String tipoCuenta;
    private String numeroCuenta;
    private String estado;
    private BigDecimal saldo;
    private Boolean exentaGMF;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private Long clienteId;
}
