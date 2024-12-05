package com.api.transporte.sistematransporte.service;

import com.api.transporte.sistematransporte.dto.ConductorDTO;
import com.api.transporte.sistematransporte.model.Conductor;
import com.api.transporte.sistematransporte.repository.ConductorRepository;
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

class ConductorServiceTest {

    @Mock
    private ConductorRepository conductorRepository;

    @InjectMocks
    private ConductorService conductorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarConductores() {
        Conductor conductor1 = new Conductor();
        conductor1.setId(1L);
        conductor1.setIdentificacion("123456789");
        conductor1.setNombre("Juan");
        conductor1.setApellido("Pérez");
        conductor1.setTelefono("3001234567");
        conductor1.setDireccion("Calle 10 #20-30");

        Conductor conductor2 = new Conductor();
        conductor2.setId(2L);
        conductor2.setIdentificacion("987654321");
        conductor2.setNombre("Ana");
        conductor2.setApellido("Gómez");
        conductor2.setTelefono("3019876543");
        conductor2.setDireccion("Carrera 15 #45-60");

        when(conductorRepository.findAll()).thenReturn(Arrays.asList(conductor1, conductor2));

        List<ConductorDTO> conductores = conductorService.listarConductores();

        assertNotNull(conductores);
        assertEquals(2, conductores.size());
        assertEquals("Juan", conductores.get(0).getNombre());
        assertEquals("Ana", conductores.get(1).getNombre());
        verify(conductorRepository, times(1)).findAll();
    }

    @Test
    void testRegistrarConductor() {
        ConductorDTO dto = new ConductorDTO();
        dto.setIdentificacion("123456789");
        dto.setNombre("Carlos");
        dto.setApellido("López");
        dto.setTelefono("3101234567");
        dto.setDireccion("Calle 30 #40-50");

        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setIdentificacion("123456789");
        conductor.setNombre("Carlos");
        conductor.setApellido("López");
        conductor.setTelefono("3101234567");
        conductor.setDireccion("Calle 30 #40-50");

        when(conductorRepository.findByIdentificacion("123456789")).thenReturn(Optional.empty());
        when(conductorRepository.save(any(Conductor.class))).thenReturn(conductor);

        ConductorDTO resultado = conductorService.registrarConductor(dto);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("López", resultado.getApellido());
        assertEquals("123456789", resultado.getIdentificacion());
        verify(conductorRepository, times(1)).save(any(Conductor.class));
    }

    @Test
    void testRegistrarConductorIdentificacionDuplicada() {
        ConductorDTO dto = new ConductorDTO();
        dto.setIdentificacion("123456789");
        dto.setNombre("Carlos");
        dto.setApellido("López");
        dto.setTelefono("3101234567");
        dto.setDireccion("Calle 30 #40-50");

        Conductor existente = new Conductor();
        existente.setId(1L);
        existente.setIdentificacion("123456789");

        when(conductorRepository.findByIdentificacion("123456789")).thenReturn(Optional.of(existente));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conductorService.registrarConductor(dto);
        });

        assertEquals("Ya existe un conductor con la identificación proporcionada.", exception.getMessage());
        verify(conductorRepository, never()).save(any(Conductor.class));
    }

    @Test
    void testRegistrarConductorDatosIncompletos() {
        ConductorDTO dto = new ConductorDTO();
        dto.setNombre("Carlos");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            conductorService.registrarConductor(dto);
        });

        assertEquals("La identificación es obligatoria.", exception.getMessage());
        verify(conductorRepository, never()).save(any(Conductor.class));
    }
}
