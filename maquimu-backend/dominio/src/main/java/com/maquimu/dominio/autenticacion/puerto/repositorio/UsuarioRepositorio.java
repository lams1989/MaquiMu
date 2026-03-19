package com.maquimu.dominio.autenticacion.puerto.repositorio;

import com.maquimu.dominio.autenticacion.modelo.Usuario;

public interface UsuarioRepositorio {
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
}
