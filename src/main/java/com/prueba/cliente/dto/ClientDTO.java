package com.prueba.cliente.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    @NotBlank(message = "El campo 'nombre' es obligatorio")
    @Size(max = 100, message = "El campo 'nombre' no puede tener más de 100 caracteres")
    private String nombre;

    @Schema(description = "Nombre del cliente", example = "Juan Perez")
    @NotBlank(message = "El campo 'genero' es obligatorio")
    @Pattern(regexp = "^[FM]$", message = "El campo 'genero' debe ser 'F' o 'M'")
    private String genero;

    @NotNull(message = "El campo 'edad' es obligatorio")
    @Min(value = 1, message = "El cliente debe tener una edad mayor a cero")
    @Max(value = 100, message = "La edad no puede ser mayor a 100 años")
    private Integer edad;

    @NotBlank(message = "El campo 'identificacion' es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "Identificación debe tener 10 dígitos")
    private String identificacion;

    @NotBlank(message = "El campo 'direccion' es obligatorio")
    @Size(max = 250, message = "El campo 'direccion' no puede tener más de 250 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,áéíóúÁÉÍÓÚñÑüÜ-]+$", message = "El campo 'direccion' no puede contener caracteres especiales")
    private String direccion;

    @NotBlank(message = "El campo 'telefono' es obligatorio")
    @Size(max = 15, message = "El campo 'telefono' no puede tener más de 15 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El campo 'telefono' solo puede contener números")
    private String telefono;

    @NotBlank(message = "El campo 'clienteId' es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "El campo 'clienteId' solo permite caracteres alfanuméricos, guiones bajos y puntos sin especios en blanco")
    @Size(min = 5, max = 20, message = "El campo 'clienteId' debe tener entre 5 y 20 caracteres")
    private String clienteId;

    @NotBlank(message = "El campo 'contrasena' es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9@$.\\-+*]+$", message = "El campo 'contrasena' no puede contener espacios en blanco y solo permite caracteres alfanuméricos y los siguientes caracteres especiales @$.-+*")
    @Size(min = 8, max = 20, message = "El campo 'contrasena' debe tener entre 8 y 20 caracteres")
    private String contrasena;

    @NotNull(message = "El campo 'estado' es obligatorio")
    @Pattern(regexp = "true|false", message = "El campo 'estado' debe ser 'true' o 'false'")
    private String estado;
}

