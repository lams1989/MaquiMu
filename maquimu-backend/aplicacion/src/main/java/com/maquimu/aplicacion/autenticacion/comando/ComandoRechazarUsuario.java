package com.maquimu.aplicacion.autenticacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRechazarUsuario {
    private Long usuarioId;
    private String motivo;
}
