package com.maquimu.infraestructura.configuracion;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManejadorGlobalExcepcionesTest {

    private final ManejadorGlobalExcepciones manejador = new ManejadorGlobalExcepciones();

    @Test
    void manejarDataIntegrityViolation_duplicado_deberiaResponderMensajeEstandar() {
        DataIntegrityViolationException excepcion =
                new DataIntegrityViolationException("Duplicate entry 'ABC123' for key 'serial_UNIQUE'");

        ResponseEntity<Map<String, Object>> respuesta = manejador.manejarDataIntegrityViolation(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(400, respuesta.getBody().get("status"));
        assertEquals("Bad Request", respuesta.getBody().get("error"));
        assertEquals("Ya existe un registro con la información proporcionada", respuesta.getBody().get("message"));
        assertNotNull(respuesta.getBody().get("timestamp"));
    }

    @Test
    void manejarDataIntegrityViolation_integridadGeneral_deberiaResponderMensajeEstandar() {
        DataIntegrityViolationException excepcion =
                new DataIntegrityViolationException("Cannot add or update a child row: a foreign key constraint fails");

        ResponseEntity<Map<String, Object>> respuesta = manejador.manejarDataIntegrityViolation(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(400, respuesta.getBody().get("status"));
        assertEquals("Bad Request", respuesta.getBody().get("error"));
        assertEquals("No se pudo procesar la solicitud por restricciones de integridad de datos", respuesta.getBody().get("message"));
        assertNotNull(respuesta.getBody().get("timestamp"));
    }
}
