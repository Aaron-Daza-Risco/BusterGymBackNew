package com.version.gymModuloControl.repository;

import java.util.List;
import java.util.Optional;

import com.version.gymModuloControl.model.EstadoInscripcion;
import com.version.gymModuloControl.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import com.version.gymModuloControl.model.Cliente;
import com.version.gymModuloControl.model.Persona;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByEstado(Boolean estado);
    Optional<Cliente> findByPersona(Persona persona);
}

