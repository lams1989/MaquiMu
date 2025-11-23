package com.xm.autenticacion.consulta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRefrescarToken {
    private String tokenDeAcceso;
    private String tokenDeRefresco;
}
