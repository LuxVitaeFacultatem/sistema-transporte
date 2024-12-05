package com.api.transporte.sistematransporte.service;

import com.api.transporte.sistematransporte.dto.ConductorDTO;
import com.api.transporte.sistematransporte.model.Conductor;
import com.api.transporte.sistematransporte.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    public List<ConductorDTO> listarConductores() {
        return conductorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ConductorDTO registrarConductor(ConductorDTO dto) {

        if (dto.getIdentificacion() == null || dto.getIdentificacion().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }
        if (dto.getNombre() == null || dto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (dto.getApellido() == null || dto.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (dto.getTelefono() == null || dto.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        if (dto.getDireccion() == null || dto.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }

        // Validación de identificaciones duplicadas
        Optional<Conductor> existente = conductorRepository.findByIdentificacion(dto.getIdentificacion());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un conductor con la identificación proporcionada.");
        }

        Conductor conductor = convertToEntity(dto);
        conductor = conductorRepository.save(conductor);
        return convertToDTO(conductor);
    }

    private ConductorDTO convertToDTO(Conductor conductor) {
        ConductorDTO dto = new ConductorDTO();
        dto.setId(conductor.getId());
        dto.setIdentificacion(conductor.getIdentificacion());
        dto.setApellido(conductor.getApellido());
        dto.setNombre(conductor.getNombre());
        dto.setTelefono(conductor.getTelefono());
        dto.setDireccion(conductor.getDireccion());
        return dto;
    }

    private Conductor convertToEntity(ConductorDTO dto) {
        Conductor conductor = new Conductor();
        conductor.setIdentificacion(dto.getIdentificacion());
        conductor.setApellido(dto.getApellido());
        conductor.setNombre(dto.getNombre());
        conductor.setTelefono(dto.getTelefono());
        conductor.setDireccion(dto.getDireccion());
        return conductor;
    }
}
