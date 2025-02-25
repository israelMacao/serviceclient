package com.prueba.cliente.service.mapper;

import com.prueba.cliente.dto.ClientDTO;
import com.prueba.cliente.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNombre(client.getNombre());
        dto.setGenero(client.getGenero());
        dto.setEdad(client.getEdad());
        dto.setIdentificacion(client.getIdentificacion());
        dto.setDireccion(client.getDireccion());
        dto.setTelefono(client.getTelefono());
        dto.setClienteId(client.getClienteid());
        dto.setContrasena(client.getContrasena());
        dto.setEstado(String.valueOf(client.isEstado()));
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        Client client = new Client();
        updateEntityFromDTO(dto, client);
        return client;
    }

    public void updateEntityFromDTO(ClientDTO dto, Client client) {
        client.setId(dto.getId());
        client.setNombre(dto.getNombre());
        client.setGenero(dto.getGenero());
        client.setEdad(dto.getEdad());
        client.setIdentificacion(dto.getIdentificacion());
        client.setDireccion(dto.getDireccion());
        client.setTelefono(dto.getTelefono());
        client.setClienteid(dto.getClienteId());
        client.setContrasena(dto.getContrasena());
        client.setEstado(Boolean.parseBoolean(dto.getEstado()));
    }
}
