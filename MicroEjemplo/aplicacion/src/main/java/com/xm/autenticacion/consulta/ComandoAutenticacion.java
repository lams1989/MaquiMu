package com.xm.autenticacion.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComandoAutenticacion {
    private String idObjeto;
    private String nombre;
    private String nombreCompleto;
    private String correoElectronico;
    private String tokenDeAcceso;
}
