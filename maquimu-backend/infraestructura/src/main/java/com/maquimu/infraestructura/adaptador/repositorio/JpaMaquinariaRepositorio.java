package com.maquimu.infraestructura.adaptador.repositorio;

import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import com.maquimu.infraestructura.adaptador.entidad.MaquinariaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMaquinariaRepositorio implements MaquinariaRepositorio {

    private final JpaMaquinariaRepository jpaMaquinariaRepository;

    public JpaMaquinariaRepositorio(JpaMaquinariaRepository jpaMaquinariaRepository) {
        this.jpaMaquinariaRepository = jpaMaquinariaRepository;
    }

    @Override
    public Maquinaria guardar(Maquinaria maquinaria) {
        MaquinariaEntity maquinariaEntity = MaquinariaEntity.fromMaquinaria(maquinaria);
        return jpaMaquinariaRepository.save(maquinariaEntity).toMaquinaria();
    }

    @Override
    public void eliminar(Long id) {
        jpaMaquinariaRepository.deleteById(id);
    }
}
