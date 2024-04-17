package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellido;
    private String correoElectronico;
    private LocalDate fechaNacimiento;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

    // Getters y setters omitidos por brevedad

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDate.now();
    }

    public boolean esMayorDeEdad() {
        return LocalDate.now().minusYears(18).isAfter(fechaNacimiento);
    }

    public boolean tieneProductosVinculados() {
        // Implementar lógica para verificar si el cliente tiene productos vinculados
        return false;
    }

    public boolean esCorreoElectronicoValido() {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(correoElectronico).matches();
    }

    public boolean esNombreApellidoValido() {
        return nombres.length() >= 2 && apellido.length() >= 2;
    }
}
