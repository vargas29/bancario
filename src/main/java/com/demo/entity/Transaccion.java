package com.demo.entity;


import com.demo.util.TipoTransaccion;
import jakarta.persistence.*;
import lombok.Data;



@Data

@Entity
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipo;

    private Double monto;

    @ManyToOne
    private Cuenta cuentaOrigen;

    @ManyToOne
    private Cuenta cuentaDestino;

    // getters y setters
}
