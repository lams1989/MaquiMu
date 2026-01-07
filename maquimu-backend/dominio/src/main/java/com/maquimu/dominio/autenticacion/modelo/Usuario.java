package com.maquimu.dominio.autenticacion.modelo;

import java.time.LocalDateTime;
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
public class Usuario {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String passwordHash;
    private RolUsuario rol;
    private LocalDateTime fechaCreacion;
    private Long clienteId; // ID del cliente asociado si el rol es CLIENTE
}
