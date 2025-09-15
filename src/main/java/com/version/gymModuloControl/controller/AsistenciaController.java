package com.version.gymModuloControl.controller;


import com.version.gymModuloControl.dto.AsistenciaClienteDTO;
import com.version.gymModuloControl.service.AsistenciaService;
import com.version.gymModuloControl.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/asistencia/cliente/registrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<String> registrarAsistenciaPorDNI(@RequestParam String dni) {
        try {
            asistenciaService.registrarAsistenciaPorDNI(dni);
            return ResponseEntity.ok("✅ Asistencia registrada correctamente.");
        } catch (RuntimeException e) {
            // Capturar errores de negocio: cliente no encontrado, inscripción inválida, etc.
            return ResponseEntity.badRequest().body("❌ " + e.getMessage());
        } catch (Exception e) {
            // Errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error al registrar asistencia.");
        }
    }

    @GetMapping("/asistencia/cliente/listar")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public List<AsistenciaClienteDTO> listarAsistencias() {
        return asistenciaService.listarAsistencias();
    }

}