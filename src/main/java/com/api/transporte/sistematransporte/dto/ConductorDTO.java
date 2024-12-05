package com.api.transporte.sistematransporte.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ConductorDTO {
    private Long id;

    @NotNull(message = "La identificación es obligatoria.")
    @Size(min = 8, max = 11, message = "La identificación debe tener entre 8 y 11 caracteres.")
    private String identificacion;

    @NotNull(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 20, message = "El apellido debe tener entre 2 y 20 caracteres.")
    private String apellido;

    @NotNull(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 y 20 caracteres.")
    private String nombre;

    @NotNull(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe contener 10 dígitos.")
    private String telefono;

    @NotNull(message = "La dirección es obligatoria.")
    @Size(max = 50, message = "La dirección no debe superar los 50 caracteres.")
    private String direccion;
}
