package com.api.transporte.sistematransporte.service;

import com.api.transporte.sistematransporte.dto.VehiculoDTO;
import com.api.transporte.sistematransporte.model.Conductor;
import com.api.transporte.sistematransporte.model.Vehiculo;
import com.api.transporte.sistematransporte.repository.VehiculoRepository;
import com.api.transporte.sistematransporte.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    public List<VehiculoDTO> listarVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VehiculoDTO registrarVehiculo(VehiculoDTO dto) {
        Vehiculo vehiculo = convertToEntity(dto);
        vehiculo = vehiculoRepository.save(vehiculo);
        return convertToDTO(vehiculo);
    }

    public List<VehiculoDTO> listarVehiculosNoAsignados() {
        return vehiculoRepository.findAll().stream()
                .filter(vehiculo -> vehiculo.getConductor() == null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void asociarConductor(Long vehiculoId, Long conductorId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));
        Conductor conductor = conductorRepository.findById(conductorId)
                .orElseThrow(() -> new IllegalArgumentException("Conductor no encontrado."));

        vehiculo.setConductor(conductor);
        vehiculoRepository.save(vehiculo);
    }

    public void desasociarConductor(Long vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));

        vehiculo.setConductor(null);
        vehiculoRepository.save(vehiculo);
    }

    private VehiculoDTO convertToDTO(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(vehiculo.getId());
        dto.setPlaca(vehiculo.getPlaca());
        dto.setModelo(vehiculo.getModelo());
        dto.setCapacidad(vehiculo.getCapacidad());
        dto.setConductorId(vehiculo.getConductor() != null ? vehiculo.getConductor().getId() : null);
        return dto;
    }

    private Vehiculo convertToEntity(VehiculoDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setCapacidad(dto.getCapacidad());
        return vehiculo;
    }
}
