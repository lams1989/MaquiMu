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
    private EstadoUsuario estado;
    private String motivoRechazo;
    private LocalDateTime fechaCreacion;
    private Long clienteId; // ID del cliente asociado si el rol es CLIENTE

    public void aprobar() {
        if (this.estado != EstadoUsuario.PENDIENTE_APROBACION) {
            throw new IllegalStateException("Solo se pueden aprobar usuarios en estado PENDIENTE_APROBACION. Estado actual: " + this.estado);
        }
        this.estado = EstadoUsuario.ACTIVO;
        this.motivoRechazo = null;
    }

    public void rechazar(String motivo) {
        if (this.estado != EstadoUsuario.PENDIENTE_APROBACION) {
            throw new IllegalStateException("Solo se pueden rechazar usuarios en estado PENDIENTE_APROBACION. Estado actual: " + this.estado);
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo de rechazo es requerido");
        }
        this.estado = EstadoUsuario.RECHAZADO;
        this.motivoRechazo = motivo;
    }

    public void solicitarRestablecimiento() {
        if (this.estado != EstadoUsuario.ACTIVO) {
            throw new IllegalStateException("Solo se puede solicitar restablecimiento de contraseña en estado ACTIVO. Estado actual: " + this.estado);
        }
        this.estado = EstadoUsuario.RESTABLECER;
    }

    public void resolverRestablecimiento(String nuevoPasswordHash) {
        if (this.estado != EstadoUsuario.RESTABLECER) {
            throw new IllegalStateException("Solo se puede resolver restablecimiento en estado RESTABLECER. Estado actual: " + this.estado);
        }
        if (nuevoPasswordHash == null || nuevoPasswordHash.isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña es requerida");
        }
        this.passwordHash = nuevoPasswordHash;
        this.estado = EstadoUsuario.ACTIVO;
    }

    public void cambiarPassword(String nuevoPasswordHash) {
        if (nuevoPasswordHash == null || nuevoPasswordHash.isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña es requerida");
        }
        this.passwordHash = nuevoPasswordHash;
    }
}
