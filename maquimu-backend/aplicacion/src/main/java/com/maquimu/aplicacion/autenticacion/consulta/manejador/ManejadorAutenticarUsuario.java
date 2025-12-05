package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.RespuestaAutenticacion;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManejadorAutenticarUsuario {

    private final UsuarioDao usuarioDao;
    private final ServicioHashing servicioHashing;
    private final ServicioToken servicioToken;

    public RespuestaAutenticacion ejecutar(ConsultaAutenticarUsuario consulta) {
        // 1. Buscar usuario
        Usuario usuario = usuarioDao.buscarPorEmail(consulta.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        // 2. Validar password
        if (!servicioHashing.coinciden(consulta.getPassword(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 3. Generar token
        String token = servicioToken.generarToken(usuario);

        // 4. Retornar respuesta
        return RespuestaAutenticacion.builder()
                .token(token)
                .usuario(usuario)
                .build();
    }
}
