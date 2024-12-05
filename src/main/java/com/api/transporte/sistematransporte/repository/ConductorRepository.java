package com.api.transporte.sistematransporte.repository;

import com.api.transporte.sistematransporte.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    Optional<Conductor> findByIdentificacion(String identificacion);

}
