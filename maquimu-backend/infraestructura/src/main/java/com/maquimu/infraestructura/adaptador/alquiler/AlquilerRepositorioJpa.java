package com.maquimu.infraestructura.adaptador.alquiler;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import com.maquimu.infraestructura.adaptador.entidad.AlquilerEntity;
import com.maquimu.infraestructura.adaptador.entidad.ClienteEntity;
import com.maquimu.infraestructura.adaptador.entidad.MaquinariaEntity;
import com.maquimu.infraestructura.adaptador.repositorio.JpaAlquilerRepository;
import com.maquimu.infraestructura.adaptador.repositorio.JpaClienteRepository;
import com.maquimu.infraestructura.adaptador.repositorio.JpaMaquinariaRepository;
import org.springframework.stereotype.Component;

@Component
public class AlquilerRepositorioJpa implements AlquilerRepositorio {

    private final JpaAlquilerRepository jpaAlquilerRepository;
    private final JpaClienteRepository jpaClienteRepository;
    private final JpaMaquinariaRepository jpaMaquinariaRepository;

    public AlquilerRepositorioJpa(JpaAlquilerRepository jpaAlquilerRepository,
                                   JpaClienteRepository jpaClienteRepository,
                                   JpaMaquinariaRepository jpaMaquinariaRepository) {
        this.jpaAlquilerRepository = jpaAlquilerRepository;
        this.jpaClienteRepository = jpaClienteRepository;
        this.jpaMaquinariaRepository = jpaMaquinariaRepository;
    }

    @Override
    public Alquiler guardar(Alquiler alquiler) {
        ClienteEntity clienteEntity = jpaClienteRepository.findById(alquiler.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + alquiler.getClienteId()));
        
        MaquinariaEntity maquinariaEntity = jpaMaquinariaRepository.findById(alquiler.getMaquinariaId())
                .orElseThrow(() -> new IllegalArgumentException("Maquinaria no encontrada con ID: " + alquiler.getMaquinariaId()));

        AlquilerEntity entity = AlquilerEntity.fromAlquiler(alquiler, clienteEntity, maquinariaEntity);
        AlquilerEntity savedEntity = jpaAlquilerRepository.save(entity);
        return savedEntity.toAlquiler();
    }

    @Override
    public void eliminar(Long id) {
        jpaAlquilerRepository.deleteById(id);
    }
}
