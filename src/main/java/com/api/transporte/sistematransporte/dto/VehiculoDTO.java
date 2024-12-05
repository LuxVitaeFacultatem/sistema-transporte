package com.api.transporte.sistematransporte.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class VehiculoDTO {
    private Long id;

    @NotNull(message = "La placa es obligatoria.")
    @Size(min = 6, max = 7, message = "La placa debe tener entre 6 y 7 caracteres.")
    private String placa;

    @NotNull(message = "El modelo es obligatorio.")
    @Size(min = 2, max = 4, message = "El modelo debe tener entre 2 y 4 caracteres.")
    private String modelo;

    @NotNull(message = "La capacidad es obligatoria.")
    @Pattern(regexp = "\\d+", message = "La capacidad debe ser un valor numérico.")
    private String capacidad;

    private Long conductorId;
}
