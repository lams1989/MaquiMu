package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.consulta.ConsultaBuscarMaquinaria;
import com.maquimu.aplicacion.consulta.ConsultaListarMaquinaria;
import com.maquimu.aplicacion.consulta.manejador.ManejadorBuscarMaquinaria;
import com.maquimu.aplicacion.consulta.manejador.ManejadorListarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maquimu/v1/maquinaria")
public class ConsultaControladorMaquinaria {

    private final ManejadorListarMaquinaria manejadorListarMaquinaria;
    private final ManejadorBuscarMaquinaria manejadorBuscarMaquinaria;

    public ConsultaControladorMaquinaria(ManejadorListarMaquinaria manejadorListarMaquinaria,
                                         ManejadorBuscarMaquinaria manejadorBuscarMaquinaria) {
        this.manejadorListarMaquinaria = manejadorListarMaquinaria;
        this.manejadorBuscarMaquinaria = manejadorBuscarMaquinaria;
    }

    @GetMapping
    public ResponseEntity<List<Maquinaria>> listarMaquinaria() {
        List<Maquinaria> maquinarias = manejadorListarMaquinaria.ejecutar(new ConsultaListarMaquinaria());
        return new ResponseEntity<>(maquinarias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maquinaria> buscarMaquinariaPorId(@PathVariable("id") Long id) {
        Maquinaria maquinaria = manejadorBuscarMaquinaria.ejecutar(new ConsultaBuscarMaquinaria(id));
        return new ResponseEntity<>(maquinaria, HttpStatus.OK);
    }
}
