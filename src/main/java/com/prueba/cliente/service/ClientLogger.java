package com.prueba.cliente.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.cliente.dto.ClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientLogger {

    private final ObjectMapper objectMapper;

    public void logFoundClient(ClientDTO clientDTO) {
        try {
            String response = objectMapper.writeValueAsString(clientDTO);
            log.info("Cliente encontrado con ID: {}. Resultado: {}", clientDTO.getId(), response);
        } catch (Exception e) {
            log.error("Error al convertir ClientDTO a JSON", e);
        }
    }

    public void logClientInfo(ClientDTO clientDTO) {
        log.info("Resultado: {}", clientDTO);
    }
}
