package com.api.transporte.sistematransporte.service;

import com.api.transporte.sistematransporte.dto.VehiculoDTO;
import com.api.transporte.sistematransporte.model.Conductor;
import com.api.transporte.sistematransporte.model.Vehiculo;
import com.api.transporte.sistematransporte.repository.ConductorRepository;
import com.api.transporte.sistematransporte.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ConductorRepository conductorRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(1L);
        vehiculo1.setPlaca("ABC123");
        vehiculo1.setModelo("2022");
        vehiculo1.setCapacidad("1500Kg");

        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(2L);
        vehiculo2.setPlaca("DEF456");
        vehiculo2.setModelo("2023");
        vehiculo2.setCapacidad("2000Kg");

        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculo1, vehiculo2));

        List<VehiculoDTO> vehiculos = vehiculoService.listarVehiculos();

        assertEquals(2, vehiculos.size());
        assertEquals("ABC123", vehiculos.get(0).getPlaca());
        assertEquals("DEF456", vehiculos.get(1).getPlaca());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    void testRegistrarVehiculo() {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPlaca("XYZ789");
        dto.setModelo("2021");
        dto.setCapacidad("1000Kg");

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPlaca("XYZ789");
        vehiculo.setModelo("2021");
        vehiculo.setCapacidad("1000Kg");

        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        VehiculoDTO resultado = vehiculoService.registrarVehiculo(dto);

        assertEquals("XYZ789", resultado.getPlaca());
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void testListarVehiculosNoAsignados() {
        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(1L);
        vehiculo1.setPlaca("ABC123");
        vehiculo1.setModelo("2022");
        vehiculo1.setCapacidad("1500Kg");
        vehiculo1.setConductor(null);

        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculo1));

        List<VehiculoDTO> vehiculosNoAsignados = vehiculoService.listarVehiculosNoAsignados();

        assertEquals(1, vehiculosNoAsignados.size());
        assertEquals("ABC123", vehiculosNoAsignados.get(0).getPlaca());
        assertNull(vehiculosNoAsignados.get(0).getConductorId());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    void testAsociarConductor() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPlaca("ABC123");
        vehiculo.setModelo("2022");

        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Juan");

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(conductorRepository.findById(1L)).thenReturn(Optional.of(conductor));

        vehiculoService.asociarConductor(1L, 1L);

        assertEquals(1L, vehiculo.getConductor().getId());
        verify(vehiculoRepository, times(1)).save(vehiculo);
    }

    @Test
    void testAsociarConductorVehiculoNoEncontrado() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.asociarConductor(1L, 1L);
        });

        assertEquals("Vehículo no encontrado.", exception.getMessage());
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void testDesasociarConductor() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPlaca("ABC123");
        vehiculo.setConductor(new Conductor());

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        vehiculoService.desasociarConductor(1L);

        assertNull(vehiculo.getConductor());
        verify(vehiculoRepository, times(1)).save(vehiculo);
    }

    @Test
    void testDesasociarConductorVehiculoNoEncontrado() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.desasociarConductor(1L);
        });

        assertEquals("Vehículo no encontrado.", exception.getMessage());
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }
}
