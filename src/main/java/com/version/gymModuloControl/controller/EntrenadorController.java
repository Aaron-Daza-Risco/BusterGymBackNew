package com.version.gymModuloControl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entrenador")
public class EntrenadorController {

    // @Autowired
    // private InscripcionService inscripcionService;

    // Endpoint para entrenadores premium
    /*
    @GetMapping("/premium/planes-clientes")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<List<PlanConClientesDTO>> obtenerPlanesClientesPremium() {
        return ResponseEntity.ok(inscripcionService.obtenerPlanesClientesPremium());
    }

    // Endpoint para entrenadores estándar
    @GetMapping("/estandar/planes-clientes")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<List<PlanConClientesDTO>> obtenerPlanesClientesEstandar() {
        return ResponseEntity.ok(inscripcionService.obtenerPlanesClientesEstandar());
    }
    */
}
