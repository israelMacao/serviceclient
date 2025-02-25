package com.prueba.cliente.controller;

import com.prueba.cliente.dto.ApiResponseClient;
import com.prueba.cliente.dto.ClientDTO;
import com.prueba.cliente.dto.ResponseProcess;
import com.prueba.cliente.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClientController {
    private final ClientService clienteService;

    @Operation(summary = "Obtener cliente por ID", description = "Retorna un cliente según el ID proporcionado")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseClient<ClientDTO>> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente con los datos proporcionados")
    @PostMapping
    public ResponseEntity<ApiResponseClient<ClientDTO>> createCliente(@Valid @RequestBody ClientDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.create(clienteDTO));
    }

    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista con todos los clientes")
    @GetMapping
    public ResponseEntity<ApiResponseClient<List<ClientDTO>>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseClient<ClientDTO>> updateCliente(@PathVariable Long id, @Valid @RequestBody ClientDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.update(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseClient<Void>> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        ResponseProcess responseProcess = new ResponseProcess("0", "Cliente eliminado correctamente", "OK");
        ApiResponseClient<Void> response = new ApiResponseClient<>(null, responseProcess);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente según el ID proporcionado")
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<ApiResponseClient<ClientDTO>> getClienteByIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(clienteService.findByIdentificacion(identificacion));
    }
}

