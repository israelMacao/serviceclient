package com.prueba.cliente.repository;

import com.prueba.cliente.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
    boolean existsByClienteid(String clienteid);
}
