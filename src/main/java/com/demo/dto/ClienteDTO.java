package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data

public class ClienteDTO {
    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String apellido;
    private String correoElectronico;
    private LocalDate fechaNacimiento;

    // Omito getters y setters por brevedad
}
