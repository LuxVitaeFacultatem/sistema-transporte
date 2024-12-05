package com.api.transporte.sistematransporte.controller;

import com.api.transporte.sistematransporte.dto.ConductorDTO;
import com.api.transporte.sistematransporte.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping
    public List<ConductorDTO> listarConductores() {
        return conductorService.listarConductores();
    }

    @PostMapping
    public ResponseEntity<ConductorDTO> registrarConductor(@Valid @RequestBody ConductorDTO dto) {
        return ResponseEntity.ok(conductorService.registrarConductor(dto));
    }
}
