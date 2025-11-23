package com.xm.autenticacion.consulta.fabrica;

import com.xm.autenticacion.consulta.ComandoRefrescarToken;
import com.xm.autenticacion.modelo.entidad.RefrescarToken;
import org.springframework.stereotype.Component;

@Component
public class FabricaRefrescarToken {

    public RefrescarToken ejecutar(ComandoRefrescarToken comando) {
        return new RefrescarToken(comando.getTokenDeAcceso(), comando.getTokenDeRefresco());
    }
}
