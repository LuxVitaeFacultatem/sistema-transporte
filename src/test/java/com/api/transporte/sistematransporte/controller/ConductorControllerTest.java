package com.api.transporte.sistematransporte.controller;

import com.api.transporte.sistematransporte.dto.ConductorDTO;
import com.api.transporte.sistematransporte.service.ConductorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConductorControllerTest {

    @Mock
    private ConductorService conductorService;

    @InjectMocks
    private ConductorController conductorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarConductores() {
        // Arrange
        ConductorDTO conductor1 = new ConductorDTO();
        conductor1.setId(1L);
        conductor1.setIdentificacion("123456789");
        conductor1.setNombre("Juan");
        conductor1.setApellido("Pérez");
        conductor1.setTelefono("3001234567");
        conductor1.setDireccion("Calle 10 #20-30");

        ConductorDTO conductor2 = new ConductorDTO();
        conductor2.setId(2L);
        conductor2.setIdentificacion("987654321");
        conductor2.setNombre("Ana");
        conductor2.setApellido("Gómez");
        conductor2.setTelefono("3019876543");
        conductor2.setDireccion("Carrera 15 #45-60");

        when(conductorService.listarConductores()).thenReturn(Arrays.asList(conductor1, conductor2));

        // Act
        List<ConductorDTO> conductores = conductorController.listarConductores();

        // Assert
        assertNotNull(conductores);
        assertEquals(2, conductores.size());
        assertEquals("Juan", conductores.get(0).getNombre());
        assertEquals("Ana", conductores.get(1).getNombre());
        verify(conductorService, times(1)).listarConductores();
    }

    @Test
    void testListarConductoresListaVacia() {
        // Arrange
        when(conductorService.listarConductores()).thenReturn(Collections.emptyList());

        // Act
        List<ConductorDTO> conductores = conductorController.listarConductores();

        // Assert
        assertNotNull(conductores);
        assertEquals(0, conductores.size());
        verify(conductorService, times(1)).listarConductores();
    }

    @Test
    void testRegistrarConductor() {
        // Arrange
        ConductorDTO dto = new ConductorDTO();
        dto.setId(1L);
        dto.setIdentificacion("123456789");
        dto.setNombre("Carlos");
        dto.setApellido("López");
        dto.setTelefono("3101234567");
        dto.setDireccion("Calle 30 #40-50");

        when(conductorService.registrarConductor(any(ConductorDTO.class))).thenReturn(dto);

        // Act
        ResponseEntity<ConductorDTO> response = conductorController.registrarConductor(dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Carlos", response.getBody().getNombre());
        assertEquals("López", response.getBody().getApellido());
        assertEquals("123456789", response.getBody().getIdentificacion());
        verify(conductorService, times(1)).registrarConductor(any(ConductorDTO.class));
    }

    @Test
    void testRegistrarConductorError() {
        // Arrange
        ConductorDTO dto = new ConductorDTO();
        dto.setIdentificacion("123456789");
        dto.setNombre("Carlos");
        dto.setApellido("López");

        doThrow(new IllegalArgumentException("Error al registrar el conductor."))
                .when(conductorService).registrarConductor(any(ConductorDTO.class));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conductorController.registrarConductor(dto);
        });

        assertEquals("Error al registrar el conductor.", exception.getMessage());
        verify(conductorService, times(1)).registrarConductor(any(ConductorDTO.class));
    }
}
