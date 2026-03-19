package com.maquimu.infraestructura.autenticacion.adaptador.entidad;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, columnDefinition = "ENUM('OPERARIO', 'CLIENTE')")
    private RolUsuario rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta", nullable = false, columnDefinition = "ENUM('PENDIENTE_APROBACION', 'ACTIVO', 'RECHAZADO', 'RESTABLECER')")
    private EstadoUsuario estadoCuenta;

    @Column(name = "motivo_rechazo", length = 500)
    private String motivoRechazo;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoCuenta == null) {
            estadoCuenta = EstadoUsuario.ACTIVO;
        }
    }

    public Usuario toDomain() {
        return Usuario.builder()
                .id(this.id)
                .nombreCompleto(this.nombreCompleto)
                .email(this.email)
                .passwordHash(this.passwordHash)
                .rol(this.rol)
                .estado(this.estadoCuenta)
                .motivoRechazo(this.motivoRechazo)
                .fechaCreacion(this.fechaCreacion)
                .build();
    }

    public static UsuarioEntity fromDomain(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .passwordHash(usuario.getPasswordHash())
                .rol(usuario.getRol())
                .estadoCuenta(usuario.getEstado())
                .motivoRechazo(usuario.getMotivoRechazo())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}
