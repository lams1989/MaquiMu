package com.maquimu.aplicacion.autenticacion.comando;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRegistrarUsuario {
    private String nombreCompleto;
    private String email;
    private String password;
    private RolUsuario rol;
    private String identificacion; // Requerido para usuarios con rol CLIENTE
}
