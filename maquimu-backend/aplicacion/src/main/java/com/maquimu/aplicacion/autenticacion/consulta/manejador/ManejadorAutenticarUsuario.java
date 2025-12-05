package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.RespuestaAutenticacion;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioToken; // GeneradorJwt implements ServicioToken
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManejadorAutenticarUsuario {

    private final UsuarioDao usuarioDao; // Still need to retrieve full User object
    private final ServicioToken servicioToken;
    private final AuthenticationManager authenticationManager;

    public RespuestaAutenticacion ejecutar(ConsultaAutenticarUsuario consulta) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(consulta.getEmail(), consulta.getPassword())
        );

        // If authentication is successful, retrieve UserDetails and generate token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // Retrieve the full Usuario object from the database using email from UserDetails
        Usuario usuario = usuarioDao.buscarPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado despues de autenticacion"));

        String token = servicioToken.generarToken(usuario);

        return RespuestaAutenticacion.builder()
                .token(token)
                .usuario(usuario)
                .build();
    }
}
