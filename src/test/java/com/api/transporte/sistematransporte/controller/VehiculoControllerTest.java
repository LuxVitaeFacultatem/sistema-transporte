package com.api.transporte.sistematransporte.controller;

import com.api.transporte.sistematransporte.dto.VehiculoDTO;
import com.api.transporte.sistematransporte.service.VehiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehiculoControllerTest {

    @Mock
    private VehiculoService vehiculoService;

    @InjectMocks
    private VehiculoController vehiculoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarVehiculos() {
        // Arrange
        VehiculoDTO vehiculo1 = new VehiculoDTO();
        vehiculo1.setId(1L);
        vehiculo1.setPlaca("ABC123");
        vehiculo1.setModelo("2022");
        vehiculo1.setCapacidad("1500Kg");

        VehiculoDTO vehiculo2 = new VehiculoDTO();
        vehiculo2.setId(2L);
        vehiculo2.setPlaca("XYZ789");
        vehiculo2.setModelo("2023");
        vehiculo2.setCapacidad("2000Kg");

        when(vehiculoService.listarVehiculos()).thenReturn(Arrays.asList(vehiculo1, vehiculo2));

        // Act
        List<VehiculoDTO> vehiculos = vehiculoController.listarVehiculos();

        // Assert
        assertNotNull(vehiculos);
        assertEquals(2, vehiculos.size());
        assertEquals("ABC123", vehiculos.get(0).getPlaca());
        assertEquals("XYZ789", vehiculos.get(1).getPlaca());
        verify(vehiculoService, times(1)).listarVehiculos();
    }

    @Test
    void testRegistrarVehiculo() {
        // Arrange
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPlaca("DEF456");
        dto.setModelo("2021");
        dto.setCapacidad("1000Kg");

        when(vehiculoService.registrarVehiculo(any(VehiculoDTO.class))).thenReturn(dto);

        // Act
        ResponseEntity<VehiculoDTO> response = vehiculoController.registrarVehiculo(dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("DEF456", response.getBody().getPlaca());
        verify(vehiculoService, times(1)).registrarVehiculo(any(VehiculoDTO.class));
    }

    @Test
    void testListarVehiculosNoAsignados() {
        // Arrange
        VehiculoDTO vehiculo1 = new VehiculoDTO();
        vehiculo1.setId(1L);
        vehiculo1.setPlaca("ABC123");
        vehiculo1.setModelo("2022");
        vehiculo1.setCapacidad("1500Kg");

        when(vehiculoService.listarVehiculosNoAsignados()).thenReturn(Arrays.asList(vehiculo1));

        // Act
        ResponseEntity<List<VehiculoDTO>> response = vehiculoController.listarVehiculosNoAsignados();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("ABC123", response.getBody().get(0).getPlaca());
        verify(vehiculoService, times(1)).listarVehiculosNoAsignados();
    }

    @Test
    void testListarVehiculosNoAsignadosVacio() {
        // Arrange
        when(vehiculoService.listarVehiculosNoAsignados()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<VehiculoDTO>> response = vehiculoController.listarVehiculosNoAsignados();

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(vehiculoService, times(1)).listarVehiculosNoAsignados();
    }

    @Test
    void testAsociarConductor() {
        // Arrange
        Long vehiculoId = 1L;
        Long conductorId = 2L;

        doNothing().when(vehiculoService).asociarConductor(vehiculoId, conductorId);

        // Act
        ResponseEntity<Map<String, String>> response = vehiculoController.asociarConductor(vehiculoId, conductorId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Conductor asociado correctamente al vehículo.", response.getBody().get("message"));
        verify(vehiculoService, times(1)).asociarConductor(vehiculoId, conductorId);
    }

    @Test
    void testDesasociarConductor() {
        // Arrange
        Long vehiculoId = 1L;

        doNothing().when(vehiculoService).desasociarConductor(vehiculoId);

        // Act
        ResponseEntity<Map<String, String>> response = vehiculoController.desasociarConductor(vehiculoId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Conductor desasociado correctamente del vehículo.", response.getBody().get("message"));
        verify(vehiculoService, times(1)).desasociarConductor(vehiculoId);
    }

    @Test
    void testAsociarConductorVehiculoNoExistente() {
        // Arrange
        Long vehiculoId = 99L;
        Long conductorId = 2L;

        doThrow(new IllegalArgumentException("Vehículo no encontrado."))
                .when(vehiculoService).asociarConductor(vehiculoId, conductorId);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoController.asociarConductor(vehiculoId, conductorId);
        });

        assertEquals("Vehículo no encontrado.", exception.getMessage());
        verify(vehiculoService, times(1)).asociarConductor(vehiculoId, conductorId);
    }
}
