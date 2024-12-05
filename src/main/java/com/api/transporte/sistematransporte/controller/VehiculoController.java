package com.api.transporte.sistematransporte.controller;

import com.api.transporte.sistematransporte.dto.VehiculoDTO;
import com.api.transporte.sistematransporte.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<VehiculoDTO> listarVehiculos() {
        return vehiculoService.listarVehiculos();
    }

    @PostMapping
    public ResponseEntity<VehiculoDTO> registrarVehiculo(@RequestBody VehiculoDTO dto) {
        return ResponseEntity.ok(vehiculoService.registrarVehiculo(dto));
    }

    @GetMapping("/no-asignados")
    public ResponseEntity<List<VehiculoDTO>> listarVehiculosNoAsignados() {
        List<VehiculoDTO> vehiculos = vehiculoService.listarVehiculosNoAsignados();
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }

    @PostMapping("/{id}/asociar-conductor/{conductorId}")
    public ResponseEntity<Map<String, String>> asociarConductor(@PathVariable Long id, @PathVariable Long conductorId) {
        vehiculoService.asociarConductor(id, conductorId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Conductor asociado correctamente al vehículo.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/desasociar-conductor")
    public ResponseEntity<Map<String, String>> desasociarConductor(@PathVariable Long id) {
        vehiculoService.desasociarConductor(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Conductor desasociado correctamente del vehículo.");
        return ResponseEntity.ok(response);
    }
}
