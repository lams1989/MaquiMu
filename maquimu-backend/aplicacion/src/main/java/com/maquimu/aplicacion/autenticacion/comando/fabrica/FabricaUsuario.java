package com.maquimu.aplicacion.autenticacion.comando.fabrica;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FabricaUsuario {

    public Usuario crear(ComandoRegistrarUsuario comando, String passwordHash) {
        return Usuario.builder()
                .nombreCompleto(comando.getNombreCompleto())
                .email(comando.getEmail())
                .passwordHash(passwordHash)
                .rol(comando.getRol())
                .build();
    }
}
