package com.prueba.cliente.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientEntity() {

        Client client = new Client();
        client.setId(1L);
        client.setNombre("Juan Perez");
        client.setGenero("Masculino");
        client.setEdad(30);
        client.setIdentificacion("123456789");
        client.setDireccion("Calle Falsa 123");
        client.setTelefono("555-1234");
        client.setClienteid("juanperez");
        client.setContrasena("password123");
        client.setEstado(true);

        // Verificar los valores asignados
        assertEquals(1L, client.getId());
        assertEquals("Juan Perez", client.getNombre());
        assertEquals("Masculino", client.getGenero());
        assertEquals(30, client.getEdad());
        assertEquals("123456789", client.getIdentificacion());
        assertEquals("Calle Falsa 123", client.getDireccion());
        assertEquals("555-1234", client.getTelefono());
        assertEquals("juanperez", client.getClienteid());
        assertEquals("password123", client.getContrasena());
        assertTrue(client.isEstado());

        //Validamos herencia
        assertNotNull(client);
        assertTrue(client instanceof Person);
    }
}