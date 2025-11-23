package com.xm.autenticacion.consulta.manejador.autenticacion;

import org.springframework.stereotype.Component;

import com.xm.autenticacion.consulta.ComandoRefrescarToken;
import com.xm.autenticacion.consulta.fabrica.FabricaRefrescarToken;
import com.xm.autenticacion.modelo.entidad.RefrescarToken;
import com.xm.autenticacion.servicio.ServicioRefrescarToken;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;

@Component
public class ManejadorConsultaRefrescarToken {

    private final FabricaRefrescarToken fabricaRefrescarToken;
    private final ServicioRefrescarToken servicioRefrescarToken;

    public ManejadorConsultaRefrescarToken(FabricaRefrescarToken fabricaRefrescarToken,
                                           ServicioRefrescarToken servicioRefrescarToken) {
        this.fabricaRefrescarToken = fabricaRefrescarToken;
        this.servicioRefrescarToken = servicioRefrescarToken;
    }

    public DtoRespuestaAutenticacion ejecutar(ComandoRefrescarToken comando) {
        RefrescarToken datosRefrescarToken = fabricaRefrescarToken.ejecutar(comando);
        return servicioRefrescarToken.ejecutar(datosRefrescarToken);
    }
}
