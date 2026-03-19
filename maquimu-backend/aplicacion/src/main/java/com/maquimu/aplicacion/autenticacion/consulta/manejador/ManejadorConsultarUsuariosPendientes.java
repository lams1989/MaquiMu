package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ManejadorConsultarUsuariosPendientes {

    private final UsuarioDao usuarioDao;

    public List<Usuario> ejecutar() {
        return usuarioDao.buscarPorEstado(EstadoUsuario.PENDIENTE_APROBACION);
    }
}
