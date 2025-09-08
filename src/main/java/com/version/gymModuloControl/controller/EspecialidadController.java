package com.version.gymModuloControl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.version.gymModuloControl.model.Especialidad;
import com.version.gymModuloControl.service.EspecialidadService;

@RestController
@RequestMapping("/api/especialidad")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

  
    
    @GetMapping("/listar-basico")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'ENTRENADOR')")
    public ResponseEntity<?> listarEspecialidadesBasico() {
        try {
            return ResponseEntity.ok(especialidadService.listarEspecialidadesBasico());
        } catch (Exception e) {
            System.err.println("Error al listar especialidades básico: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al listar especialidades: " + e.getMessage());
        }
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Especialidad> guardarEspecialidad(@RequestBody Especialidad especialidad) {
        Especialidad guardada = especialidadService.guardarEspecialidad(especialidad);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Especialidad> actualizarEspecialidad(@RequestBody Especialidad especialidad) {
        Especialidad actualizada = especialidadService.actualizarEspecialidad(especialidad);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/cambiarEstado/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Especialidad> cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado) {
        try {
            Especialidad especialidad = especialidadService.cambiarEstado(id, estado);
            return ResponseEntity.ok(especialidad);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarEspecialidad(@PathVariable Integer id) {
        boolean eliminada = especialidadService.eliminarEspecialidad(id);
        if (eliminada) {
            return ResponseEntity.ok().body("Especialidad eliminada correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
