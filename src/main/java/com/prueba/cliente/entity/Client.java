package com.prueba.cliente.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Client extends Person {

    @Column(unique = true)
    private String clienteid;
    private String contrasena;
    private boolean estado;
}
