package com.demo.dto;

import com.demo.util.TipoTransaccion;
import lombok.Data;

@Data
public class TransaccionDTO {
    private Long id;
    private TipoTransaccion tipo;
    private Double monto;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;

    // getters y setters
}