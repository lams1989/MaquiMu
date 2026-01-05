package com.maquimu.aplicacion.cliente.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComandoCrearCliente {
    private String nombreCliente;
    private String identificacion;
    private String telefono;
    private String email;
    private String direccion;
}
