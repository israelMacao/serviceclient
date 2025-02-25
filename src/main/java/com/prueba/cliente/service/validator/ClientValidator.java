package com.prueba.cliente.service.validator;

import com.prueba.cliente.dto.ClientDTO;
import com.prueba.cliente.entity.Client;
import com.prueba.cliente.exception.BankBusinessException;
import com.prueba.cliente.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository clientRepository;

    private static final String DUPLICATE_IDENTIFICATION = "Ya existe un cliente con la identificación: ";
    private static final String DUPLICATE_CLIENT_ID = "Ya existe un cliente con el ID: ";
    private static final String NOT_FOUND_IDENTIFICATION = "No existe cliente con la identificacion enviada para actualizar: ";

    public void validateCreateClient(ClientDTO clientDTO) {
        validateRequiredFields(clientDTO);
        validateUniqueIdentification(clientDTO.getIdentificacion());
        validateUniqueClientId(clientDTO.getClienteId());
    }

    public void validateUpdateClient(Client existingClient, ClientDTO clientDTO) {
        validateRequiredFields(clientDTO);
        validateIdentifiersForUpdate(existingClient, clientDTO);
    }

    private void validateRequiredFields(ClientDTO clientDTO) {
        List<String> missingFields = new ArrayList<>();

        validateField(missingFields, "nombre", () -> isNullOrEmpty(clientDTO.getNombre()));
        validateField(missingFields, "genero", () -> isNullOrEmpty(clientDTO.getGenero()));
        validateField(missingFields, "edad", () -> clientDTO.getEdad() == null);
        validateField(missingFields, "identificacion", () -> isNullOrEmpty(clientDTO.getIdentificacion()));
        validateField(missingFields, "direccion", () -> isNullOrEmpty(clientDTO.getDireccion()));
        validateField(missingFields, "telefono", () -> isNullOrEmpty(clientDTO.getTelefono()));
        validateField(missingFields, "contrasena", () -> isNullOrEmpty(clientDTO.getContrasena()));
        validateField(missingFields, "estado", () -> clientDTO.getEstado() == null);
        validateField(missingFields, "clienteId", () -> isNullOrEmpty(clientDTO.getClienteId()));

        if (!missingFields.isEmpty()) {
            throw new BankBusinessException("Campos obligatorios faltantes: " + String.join(", ", missingFields));
        }
    }

    private void validateUniqueIdentification(String identification) {
        if (isNullOrEmpty(identification)) {
            throw new BankBusinessException("La identificación es requerida");
        }

        if (clientRepository.existsByIdentificacion(identification)) {
            throw new BankBusinessException(DUPLICATE_IDENTIFICATION + identification);
        }
    }

    private void validateUniqueClientId(String clientId) {
        if (clientRepository.existsByClienteid(clientId)) {
            throw new BankBusinessException(DUPLICATE_CLIENT_ID + clientId);
        }
    }

    private void validateIdentifiersForUpdate(Client existingClient, ClientDTO clientDTO) {
        // Verifica si el nuevo clienteId ya existe en otro registro
        if (!existingClient.getClienteid().equals(clientDTO.getClienteId()) &&
                clientRepository.existsByClienteid(clientDTO.getClienteId())) {
            throw new BankBusinessException(DUPLICATE_CLIENT_ID + clientDTO.getClienteId());
        }

        boolean existCliente = clientRepository.existsByIdentificacion(clientDTO.getIdentificacion());
        if(!existCliente){
            throw new BankBusinessException(NOT_FOUND_IDENTIFICATION + clientDTO.getIdentificacion());
        }

        // Verifica si la nueva identificación ya existe en otro registro
        if (!existingClient.getIdentificacion().equals(clientDTO.getIdentificacion()) && existCliente) {
            throw new BankBusinessException(DUPLICATE_IDENTIFICATION + clientDTO.getIdentificacion());
        }
    }

    private void validateField(List<String> missingFields, String fieldName, Supplier<Boolean> condition) {
        if (condition.get()) {
            missingFields.add(fieldName);
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
