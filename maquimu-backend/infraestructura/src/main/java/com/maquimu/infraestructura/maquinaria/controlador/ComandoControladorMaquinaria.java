package com.maquimu.infraestructura.maquinaria.controlador;

import com.maquimu.aplicacion.maquinaria.comando.ComandoActualizarMaquinaria;
import com.maquimu.aplicacion.maquinaria.comando.ComandoCrearMaquinaria;
import com.maquimu.aplicacion.maquinaria.comando.ComandoEliminarMaquinaria;
import com.maquimu.aplicacion.maquinaria.comando.manejador.ManejadorActualizarMaquinaria;
import com.maquimu.aplicacion.maquinaria.comando.manejador.ManejadorCrearMaquinaria;
import com.maquimu.aplicacion.maquinaria.comando.manejador.ManejadorEliminarMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maquimu/v1/maquinaria")
public class ComandoControladorMaquinaria {

    private final ManejadorCrearMaquinaria manejadorCrearMaquinaria;
    private final ManejadorActualizarMaquinaria manejadorActualizarMaquinaria;
    private final ManejadorEliminarMaquinaria manejadorEliminarMaquinaria;

    public ComandoControladorMaquinaria(ManejadorCrearMaquinaria manejadorCrearMaquinaria,
                                        ManejadorActualizarMaquinaria manejadorActualizarMaquinaria,
                                        ManejadorEliminarMaquinaria manejadorEliminarMaquinaria) {
        this.manejadorCrearMaquinaria = manejadorCrearMaquinaria;
        this.manejadorActualizarMaquinaria = manejadorActualizarMaquinaria;
        this.manejadorEliminarMaquinaria = manejadorEliminarMaquinaria;
    }

    @PostMapping
    public ResponseEntity<Maquinaria> crearMaquinaria(@RequestBody ComandoCrearMaquinaria comandoCrearMaquinaria) {
        Maquinaria nuevaMaquinaria = manejadorCrearMaquinaria.ejecutar(comandoCrearMaquinaria);
        return new ResponseEntity<>(nuevaMaquinaria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maquinaria> actualizarMaquinaria(@PathVariable("id") Long id, @RequestBody ComandoActualizarMaquinaria comandoActualizarMaquinaria) {
        comandoActualizarMaquinaria.setMaquinariaId(id);
        Maquinaria maquinariaActualizada = manejadorActualizarMaquinaria.ejecutar(comandoActualizarMaquinaria);
        return new ResponseEntity<>(maquinariaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMaquinaria(@PathVariable("id") Long id) {
        manejadorEliminarMaquinaria.ejecutar(new ComandoEliminarMaquinaria(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
