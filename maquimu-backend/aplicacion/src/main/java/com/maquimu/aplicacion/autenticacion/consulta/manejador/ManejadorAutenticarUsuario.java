package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.RespuestaAutenticacion;
import com.maquimu.dominio.autenticacion.excepcion.CuentaNoActivaException;
import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioToken;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManejadorAutenticarUsuario {

    private final UsuarioDao usuarioDao;
    private final ClienteDao clienteDao;
    private final ServicioToken servicioToken;
    private final AuthenticationManager authenticationManager;

    public RespuestaAutenticacion ejecutar(ConsultaAutenticarUsuario consulta) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(consulta.getEmail(), consulta.getPassword())
        );

        // Si la autenticación es exitosa, recupera UserDetails y genera un token.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // Recuperar el objeto Usuario completo de la base de datos usando el correo electrónico de UserDetails
        Usuario usuario = usuarioDao.buscarPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado despues de autenticacion"));

        // Validar estado de la cuenta antes de generar token
        if (usuario.getEstado() == EstadoUsuario.PENDIENTE_APROBACION) {
            throw new CuentaNoActivaException(
                    EstadoUsuario.PENDIENTE_APROBACION,
                    "Tu cuenta aún está pendiente de aprobación. Por favor espera de 1 a 3 días hábiles."
            );
        }
        if (usuario.getEstado() == EstadoUsuario.RECHAZADO) {
            throw new CuentaNoActivaException(
                    EstadoUsuario.RECHAZADO,
                    "Tu cuenta ha sido rechazada. Contacta al administrador para más información.",
                    usuario.getMotivoRechazo()
            );
        }
        if (usuario.getEstado() == EstadoUsuario.RESTABLECER) {
            throw new CuentaNoActivaException(
                    EstadoUsuario.RESTABLECER,
                    "Tu cuenta tiene una solicitud de restablecimiento de contraseña en proceso. Espera a que un operario te asigne una contraseña temporal."
            );
        }

        // Si el usuario es CLIENTE, buscar su clienteId
        if (usuario.getRol() == RolUsuario.CLIENTE) {
            Optional<Cliente> cliente = clienteDao.buscarPorUsuarioId(usuario.getId());
            cliente.ifPresent(c -> usuario.setClienteId(c.getClienteId()));
        }

        String token = servicioToken.generarToken(usuario);

        return RespuestaAutenticacion.builder()
                .token(token)
                .usuario(usuario)
                .build();
    }
}
