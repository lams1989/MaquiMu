package com.maquimu.infraestructura.adaptador.entidad;

import com.maquimu.dominio.cliente.modelo.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "usuario_id", nullable = true, unique = true)
    private Long usuarioId;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(nullable = true)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    public static ClienteEntity fromCliente(Cliente cliente) {
        return new ClienteEntity(
                cliente.getClienteId(),
                cliente.getUsuarioId(),
                cliente.getNombreCliente(),
                cliente.getIdentificacion(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getDireccion(),
                cliente.getFechaRegistro()
        );
    }

    public Cliente toCliente() {
        return Cliente.builder()
                .clienteId(clienteId)
                .usuarioId(usuarioId)
                .nombreCliente(nombreCliente)
                .identificacion(identificacion)
                .telefono(telefono)
                .email(email)
                .direccion(direccion)
                .fechaRegistro(fechaRegistro)
                .build();
    }
}
