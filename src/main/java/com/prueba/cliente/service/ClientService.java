package com.prueba.cliente.service;

import com.prueba.cliente.dto.ApiResponseClient;
import com.prueba.cliente.dto.ClientDTO;
import com.prueba.cliente.entity.Client;
import com.prueba.cliente.exception.BankBusinessException;
import com.prueba.cliente.repository.ClientRepository;
import com.prueba.cliente.service.mapper.ClientMapper;
import com.prueba.cliente.service.response.ResponseBuilderCustom;
import com.prueba.cliente.service.validator.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientValidator clientValidator;
    private final ResponseBuilderCustom responseBuilder;
    private final ClientLogger clientLogger;

    private static final String UUID_KEY = "uuid";
    private static final String CLIENT_NOT_FOUND = "Cliente no encontrado con ID: ";

    @Transactional(readOnly = true)
    public ApiResponseClient<ClientDTO> findById(Long id) {
        log.info("Buscando cliente con ID: {}", id);

        try {
            ClientDTO clientDTO = clientRepository.findById(id)
                    .map(clientMapper::toDTO)
                    .orElseThrow(() -> new EntityNotFoundException(CLIENT_NOT_FOUND + id));

            clientLogger.logFoundClient(clientDTO);
            return responseBuilder.buildSuccess(clientDTO, HttpStatus.OK, "Cliente encontrado exitosamente");
        } catch (EntityNotFoundException e) {
            log.error("Cliente no encontrado con ID: {}", id);
            return responseBuilder.buildError(HttpStatus.NOT_FOUND, "Cliente no encontrado", e.getMessage());
        } catch (Exception e) {
            log.error("Error al buscar cliente con ID {}: {}", id, e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponseClient<List<ClientDTO>> findAll() {
        log.info("Obteniendo todos los clientes");

        try {
            List<ClientDTO> clientDTOList = clientRepository.findAll().stream()
                    .map(clientMapper::toDTO)
                    .peek(clientLogger::logClientInfo)
                    .collect(Collectors.toList());

            return responseBuilder.buildSuccess(clientDTOList, HttpStatus.OK, "Clientes obtenidos exitosamente");
        } catch (Exception e) {
            log.error("Error al obtener la lista de clientes: {}", e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la lista de clientes", e.getMessage());
        }
    }

    @Transactional
    public ApiResponseClient<ClientDTO> create(ClientDTO clientDTO) {
        log.info("Creando nuevo cliente: {}", clientDTO.getClienteId());

        try {
            clientValidator.validateCreateClient(clientDTO);

            Client client = clientMapper.toEntity(clientDTO);
            Client savedClient = clientRepository.save(client);
            ClientDTO savedClientDTO = clientMapper.toDTO(savedClient);

            return responseBuilder.buildSuccess(savedClientDTO, HttpStatus.CREATED, "Cliente creado exitosamente");
        } catch (BankBusinessException e) {
            log.error("Error de negocio al crear cliente: {}", e.getMessage());
            return responseBuilder.buildError(HttpStatus.BAD_REQUEST, "Error al crear cliente", e.getMessage());
        } catch (Exception e) {
            log.error("Error al crear cliente: {}", e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud", e.getMessage());
        }
    }

    @Transactional
    public ApiResponseClient<ClientDTO> update(Long id, ClientDTO clientDTO) {
        String uuid = MDC.get(UUID_KEY);
        log.info("[UUID: {}] Actualizando cliente con ID: {}", uuid, id);

        try {
            Client existingClient = findClientById(id);
            clientValidator.validateUpdateClient(existingClient, clientDTO);
            clientDTO.setId(id);
            clientMapper.updateEntityFromDTO(clientDTO, existingClient);
            Client updatedClient = clientRepository.save(existingClient);
            ClientDTO updatedClientDTO = clientMapper.toDTO(updatedClient);

            log.info("[UUID: {}] Cliente actualizado exitosamente con ID: {}", uuid, id);
            return responseBuilder.buildSuccess(updatedClientDTO, HttpStatus.OK, "Cliente actualizado exitosamente");
        } catch (EntityNotFoundException e) {
            log.error("[UUID: {}] Cliente no encontrado: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.NOT_FOUND, "Cliente no encontrado", e.getMessage());
        } catch (BankBusinessException e) {
            log.error("[UUID: {}] Error de negocio al actualizar cliente: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.BAD_REQUEST, "Error al actualizar cliente", e.getMessage());
        } catch (Exception e) {
            log.error("[UUID: {}] Error al actualizar cliente: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud", e.getMessage());
        }
    }

    @Transactional
    public ApiResponseClient<Void> delete(Long id) {
        String uuid = MDC.get(UUID_KEY);
        log.info("[UUID: {}] Eliminando cliente con ID: {}", uuid, id);

        try {
            if (!clientRepository.existsById(id)) {
                throw new EntityNotFoundException(CLIENT_NOT_FOUND + id);
            }

            clientRepository.deleteById(id);
            return responseBuilder.buildSuccess(null, HttpStatus.OK, "Cliente eliminado exitosamente");
        } catch (EntityNotFoundException e) {
            log.error("[UUID: {}] Cliente no encontrado: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.NOT_FOUND, "Cliente no encontrado", e.getMessage());
        } catch (Exception e) {
            log.error("[UUID: {}] Error al eliminar cliente: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponseClient<ClientDTO> findByIdentificacion(String identificacion) {
        String uuid = MDC.get(UUID_KEY);
        log.info("[UUID: {}] Buscando cliente por identificación: {}", uuid, identificacion);

        try {
            ClientDTO clientDTO = clientRepository.findByIdentificacion(identificacion)
                    .map(clientMapper::toDTO)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cliente no encontrado con identificación: " + identificacion));

            return responseBuilder.buildSuccess(clientDTO, HttpStatus.OK, "Cliente encontrado exitosamente");
        } catch (EntityNotFoundException e) {
            log.error("[UUID: {}] Cliente no encontrado: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.NOT_FOUND, "Cliente no encontrado", e.getMessage());
        } catch (Exception e) {
            log.error("[UUID: {}] Error al buscar cliente: {}", uuid, e.getMessage());
            return responseBuilder.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud", e.getMessage());
        }
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CLIENT_NOT_FOUND + id));
    }
}
