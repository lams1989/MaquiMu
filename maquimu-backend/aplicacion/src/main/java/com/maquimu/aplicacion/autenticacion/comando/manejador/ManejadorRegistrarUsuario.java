package com.maquimu.aplicacion.autenticacion.comando.manejador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.fabrica.FabricaUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorRegistrarUsuario {

    private final UsuarioDao usuarioDao;
    private final UsuarioRepositorio usuarioRepositorio;
    private final FabricaUsuario fabricaUsuario;
    private final ServicioHashing servicioHashing;

    @Transactional
    public void ejecutar(ComandoRegistrarUsuario comando) {
        // 1. Validar email único
        if (usuarioDao.existePorEmail(comando.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + comando.getEmail());
        }

        // 2. Hashear password
        String passwordHash = servicioHashing.hashear(comando.getPassword());

        // 3. Crear usuario usando fábrica
        Usuario usuario = fabricaUsuario.crear(comando, passwordHash);

        // 4. Persistir
        usuarioRepositorio.guardar(usuario);
    }
}
