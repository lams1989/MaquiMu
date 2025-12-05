package com.maquimu.infraestructura.autenticacion.adaptador.repositorio;

import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.maquimu.dominio.autenticacion.modelo.Usuario usuario = usuarioDao.buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Mapear el rol del dominio al rol de Spring Security
        String role = "ROLE_" + usuario.getRol().name(); // Asumiendo que RolUsuario es un Enum con nombres como OPERARIO, CLIENTE

        return new User(usuario.getEmail(), usuario.getPasswordHash(), Collections.singletonList(() -> role));
    }
}
