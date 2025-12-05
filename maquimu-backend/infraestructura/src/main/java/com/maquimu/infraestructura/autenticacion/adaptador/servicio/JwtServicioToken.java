package com.maquimu.infraestructura.autenticacion.adaptador.servicio;

import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioToken;
import com.maquimu.infraestructura.configuracion.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServicioToken implements ServicioToken {

    private final JwtUtils jwtUtils;

    @Override
    public String generarToken(Usuario usuario) {
        return jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());
    }
}
