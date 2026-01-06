package com.maquimu.infraestructura.adaptador.entidad;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alquileres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alquiler_id")
    private Long alquilerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maquinaria_id", nullable = false)
    private MaquinariaEntity maquinaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity usuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "costo_total", nullable = false)
    private BigDecimal costoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_alquiler", nullable = false)
    private EstadoAlquiler estado;

    public static AlquilerEntity fromAlquiler(Alquiler alquiler, ClienteEntity clienteEntity, MaquinariaEntity maquinariaEntity) {
        AlquilerEntity entity = new AlquilerEntity();
        entity.setAlquilerId(alquiler.getId());
        entity.setCliente(clienteEntity);
        entity.setMaquinaria(maquinariaEntity);
        entity.setFechaInicio(alquiler.getFechaInicio());
        entity.setFechaFin(alquiler.getFechaFin());
        entity.setCostoTotal(alquiler.getCostoTotal());
        entity.setEstado(alquiler.getEstado());
        return entity;
    }

    public Alquiler toAlquiler() {
        return new Alquiler(
                alquilerId,
                cliente.getClienteId(),
                maquinaria.getMaquinariaId(),
                usuario != null ? usuario.getId().longValue() : null,
                fechaInicio,
                fechaFin,
                costoTotal,
                estado
        );
    }
}
