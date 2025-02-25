package com.prueba.cliente.controller;

import com.prueba.cliente.dto.ApiResponseClient;
import com.prueba.cliente.dto.ClientDTO;
import com.prueba.cliente.dto.ResponseProcess;
import com.prueba.cliente.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseClient<ClientDTO>> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponseClient<ClientDTO>> createCliente(@Valid @RequestBody ClientDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.create(clienteDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponseClient<List<ClientDTO>>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

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

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<ApiResponseClient<ClientDTO>> getClienteByIdentificacion(@PathVariable String identificacion) {
        return ResponseEntity.ok(clienteService.findByIdentificacion(identificacion));
    }
}

