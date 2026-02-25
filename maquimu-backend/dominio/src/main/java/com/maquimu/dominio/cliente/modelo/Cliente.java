package com.maquimu.dominio.cliente.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Getter
@Setter
@Builder
public class Cliente {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private Long clienteId;
    private Long usuarioId;
    private String nombreCliente;
    private String apellido;
    private String identificacion;
    private String telefono;
    private String email;
    private String direccion;
    private LocalDateTime fechaRegistro;

    // Constructor completo (compatibilidad)
    public Cliente(Long clienteId, Long usuarioId, String nombreCliente, String identificacion, String telefono, String email, String direccion, LocalDateTime fechaRegistro) {
        this(clienteId, usuarioId, nombreCliente, null, identificacion, telefono, email, direccion, fechaRegistro);
    }

    // Constructor completo
    public Cliente(Long clienteId, Long usuarioId, String nombreCliente, String apellido, String identificacion, String telefono, String email, String direccion, LocalDateTime fechaRegistro) {
        this.clienteId = clienteId;
        // usuarioId ahora puede ser nulo cuando se registra un cliente sin usuario asociado
        this.usuarioId = usuarioId;
        this.nombreCliente = Optional.ofNullable(nombreCliente)
            .filter(n -> !n.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("El nombre del cliente no puede ser nulo o vacío."));
        this.apellido = Optional.ofNullable(apellido)
            .map(String::trim)
            .filter(a -> !a.isBlank())
            .orElse(null);
        this.identificacion = Optional.ofNullable(identificacion)
            .filter(i -> !i.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("La identificación no puede ser nula o vacía."));
        this.email = Optional.ofNullable(email)
            .filter(e -> !e.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("El email no puede ser nulo o vacío."));
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = Optional.ofNullable(fechaRegistro).orElse(LocalDateTime.now());

        validarEmail();
    }

    // Constructor sin ID para creación
    public Cliente(Long usuarioId, String nombreCliente, String identificacion, String telefono, String email, String direccion) {
        this(null, usuarioId, nombreCliente, null, identificacion, telefono, email, direccion, LocalDateTime.now());
    }

    // Constructor sin ID para creación con apellido
    public Cliente(Long usuarioId, String nombreCliente, String apellido, String identificacion, String telefono, String email, String direccion) {
        this(null, usuarioId, nombreCliente, apellido, identificacion, telefono, email, direccion, LocalDateTime.now());
    }

    private void validarEmail() {
        if (!EMAIL_PATTERN.matcher(this.email).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }
    }

    public static boolean esEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public String getNombreCompleto() {
        if (this.apellido == null || this.apellido.isBlank()) {
            return this.nombreCliente;
        }
        return this.nombreCliente + " " + this.apellido;
    }
}
