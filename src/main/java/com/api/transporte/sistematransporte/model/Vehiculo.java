package com.api.transporte.sistematransporte.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 6, max = 7)
    private String placa;

    @Column(nullable = false, length = 4)
    @NotNull
    @Size(min = 2, max = 4)
    private String modelo;

    @Column(nullable = false, length = 7)
    @NotNull
    @Pattern(regexp = "\\d+")
    private String capacidad;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;
}
