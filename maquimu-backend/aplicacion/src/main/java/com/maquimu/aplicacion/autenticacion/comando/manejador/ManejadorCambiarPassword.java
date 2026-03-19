package com.maquimu.aplicacion.autenticacion.comando.manejador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoCambiarPassword;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorCambiarPassword {

    private final UsuarioDao usuarioDao;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ServicioHashing servicioHashing;

    @Transactional
    public void ejecutar(Long usuarioId, ComandoCambiarPassword comando) {
        Usuario usuario = usuarioDao.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId));

        if (!servicioHashing.coinciden(comando.getPasswordActual(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        if (comando.getPasswordActual().equals(comando.getPasswordNueva())) {
            throw new IllegalArgumentException("La nueva contraseña debe ser diferente a la actual");
        }

        String nuevoHash = servicioHashing.hashear(comando.getPasswordNueva());
        usuario.cambiarPassword(nuevoHash);
        usuarioRepositorio.guardar(usuario);
    }
}
