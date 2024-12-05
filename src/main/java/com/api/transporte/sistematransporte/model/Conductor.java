package com.api.transporte.sistematransporte.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    @NotNull
    @Size(min = 8, max = 11)
    private String identificacion;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(min = 2, max = 20)
    private String apellido;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(min = 2, max = 20)
    private String nombre;

    @Column(nullable = false, length = 10)
    @NotNull
    @Pattern(regexp = "\\d{10}")
    private String telefono;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String direccion;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehiculo> vehiculos;
}
