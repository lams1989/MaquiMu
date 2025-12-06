package com.maquimu.dominio.puerto.repositorio;

import com.maquimu.dominio.modelo.Maquinaria;

public interface MaquinariaRepositorio {
    Maquinaria guardar(Maquinaria maquinaria);
    void eliminar(Long id);
}
