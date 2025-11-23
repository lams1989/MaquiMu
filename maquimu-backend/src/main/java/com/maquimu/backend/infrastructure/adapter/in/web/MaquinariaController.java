package com.maquimu.backend.infrastructure.adapter.in.web;

import com.maquimu.backend.application.service.MaquinariaService;
import com.maquimu.backend.domain.model.Maquinaria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maquinarias")
@CrossOrigin(origins = "*") // Allow all origins for dev
public class MaquinariaController {

    private final MaquinariaService maquinariaService;

    public MaquinariaController(MaquinariaService maquinariaService) {
        this.maquinariaService = maquinariaService;
    }

    @GetMapping
    public ResponseEntity<List<Maquinaria>> getAllMaquinarias() {
        return ResponseEntity.ok(maquinariaService.getAllMaquinarias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maquinaria> getMaquinariaById(@PathVariable Long id) {
        return maquinariaService.getMaquinariaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMaquinaria(@RequestBody Maquinaria maquinaria) {
        try {
            Maquinaria created = maquinariaService.createMaquinaria(maquinaria);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaquinaria(@PathVariable Long id, @RequestBody Maquinaria maquinaria) {
        try {
            Maquinaria updated = maquinariaService.updateMaquinaria(id, maquinaria);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaquinaria(@PathVariable Long id) {
        try {
            maquinariaService.deleteMaquinaria(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
