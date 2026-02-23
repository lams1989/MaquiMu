package com.maquimu.dominio.cliente.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void crearCliente_conValoresValidos_deberiaCrearseCorrectamente() {
        Cliente cliente = new Cliente(
                null, null, "Juan Pérez", "1234567890", "3001234567",
                "juan@example.com", "Calle 123", LocalDateTime.now()
        );

        assertNotNull(cliente);
        assertEquals("Juan Pérez", cliente.getNombreCliente());
        assertEquals("1234567890", cliente.getIdentificacion());
        assertEquals("juan@example.com", cliente.getEmail());
        assertEquals("3001234567", cliente.getTelefono());
        assertEquals("Calle 123", cliente.getDireccion());
        assertNotNull(cliente.getFechaRegistro());
    }

    @Test
    void crearCliente_sinId_deberiaEstablecerFechaRegistroAutomatica() {
        Cliente cliente = new Cliente(
                null, "María López", "9876543210", "3109876543",
                "maria@example.com", "Carrera 45"
        );

        assertNotNull(cliente);
        assertNull(cliente.getClienteId());
        assertNotNull(cliente.getFechaRegistro());
        assertEquals("María López", cliente.getNombreCliente());
    }

    @Test
    void crearCliente_conNombreNulo_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, null, "1234567890", "3001234567",
                    "juan@example.com", "Calle 123", LocalDateTime.now());
        });
        assertEquals("El nombre del cliente no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void crearCliente_conNombreVacio_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "   ", "1234567890", "3001234567",
                    "juan@example.com", "Calle 123", LocalDateTime.now());
        });
        assertEquals("El nombre del cliente no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void crearCliente_conIdentificacionNula_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "Juan Pérez", null, "3001234567",
                    "juan@example.com", "Calle 123", LocalDateTime.now());
        });
        assertEquals("La identificación no puede ser nula o vacía.", exception.getMessage());
    }

    @Test
    void crearCliente_conIdentificacionVacia_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "Juan Pérez", "", "3001234567",
                    "juan@example.com", "Calle 123", LocalDateTime.now());
        });
        assertEquals("La identificación no puede ser nula o vacía.", exception.getMessage());
    }

    @Test
    void crearCliente_conEmailNulo_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "Juan Pérez", "1234567890", "3001234567",
                    null, "Calle 123", LocalDateTime.now());
        });
        assertEquals("El email no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void crearCliente_conEmailVacio_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "Juan Pérez", "1234567890", "3001234567",
                    "", "Calle 123", LocalDateTime.now());
        });
        assertEquals("El email no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void crearCliente_conEmailInvalido_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(null, null, "Juan Pérez", "1234567890", "3001234567",
                    "correo-invalido", "Calle 123", LocalDateTime.now());
        });
        assertEquals("El formato del email no es válido.", exception.getMessage());
    }

    @Test
    void crearCliente_conEmailValido_deberiaCrearseCorrectamente() {
        Cliente cliente = new Cliente(
                null, null, "Juan Pérez", "1234567890", "3001234567",
                "juan.perez+test@empresa.co", "Calle 123", LocalDateTime.now()
        );

        assertNotNull(cliente);
        assertEquals("juan.perez+test@empresa.co", cliente.getEmail());
    }

    @Test
    void crearCliente_conTelefonoNulo_deberiaCrearseCorrectamente() {
        Cliente cliente = new Cliente(
                null, null, "Juan Pérez", "1234567890", null,
                "juan@example.com", "Calle 123", LocalDateTime.now()
        );

        assertNotNull(cliente);
        assertNull(cliente.getTelefono());
    }

    @Test
    void crearCliente_conDireccionNula_deberiaCrearseCorrectamente() {
        Cliente cliente = new Cliente(
                null, null, "Juan Pérez", "1234567890", "3001234567",
                "juan@example.com", null, LocalDateTime.now()
        );

        assertNotNull(cliente);
        assertNull(cliente.getDireccion());
    }

    @Test
    void crearCliente_conFechaRegistroNula_deberiaEstablecerFechaAutomatica() {
        Cliente cliente = new Cliente(
                null, null, "Juan Pérez", "1234567890", "3001234567",
                "juan@example.com", "Calle 123", null
        );

        assertNotNull(cliente);
        assertNotNull(cliente.getFechaRegistro());
    }

    @Test
    void crearCliente_conUsuarioId_deberiaAsociarseCorrectamente() {
        Cliente cliente = new Cliente(
                null, 5L, "Juan Pérez", "1234567890", "3001234567",
                "juan@example.com", "Calle 123", LocalDateTime.now()
        );

        assertNotNull(cliente);
        assertEquals(5L, cliente.getUsuarioId());
    }

    @Test
    void esEmailValido_conEmailValido_deberiaRetornarTrue() {
        assertTrue(Cliente.esEmailValido("juan@example.com"));
        assertTrue(Cliente.esEmailValido("maria.lopez+tag@empresa.co"));
        assertTrue(Cliente.esEmailValido("user_name@domain.org"));
    }

    @Test
    void esEmailValido_conEmailInvalido_deberiaRetornarFalse() {
        assertFalse(Cliente.esEmailValido(null));
        assertFalse(Cliente.esEmailValido("correo-invalido"));
        assertFalse(Cliente.esEmailValido("@dominio.com"));
    }

    @Test
    void crearCliente_viaBuilder_deberiaEjecutarValidaciones() {
        // Verifica que el Builder de Lombok usa el constructor con validaciones
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Cliente.builder()
                    .nombreCliente(null)
                    .identificacion("1234567890")
                    .email("juan@example.com")
                    .build();
        });
        assertEquals("El nombre del cliente no puede ser nulo o vacío.", exception.getMessage());
    }
}
