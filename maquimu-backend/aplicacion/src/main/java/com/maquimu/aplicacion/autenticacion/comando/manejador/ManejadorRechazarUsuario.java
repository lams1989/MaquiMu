package com.maquimu.aplicacion.autenticacion.comando.manejador;

import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorRechazarUsuario {

    private final UsuarioDao usuarioDao;
    private final UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void ejecutar(Long usuarioId, String motivo) {
        Usuario usuario = usuarioDao.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId));

        usuario.rechazar(motivo);
        usuarioRepositorio.guardar(usuario);
    }
}
