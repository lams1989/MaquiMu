package com.xm.autenticacion.consulta.fabrica;

import com.xm.autenticacion.consulta.ComandoAutenticacion;
import com.xm.autenticacion.modelo.entidad.Usuario;
import org.springframework.stereotype.Component;

@Component
public class FabricaCredencialesUsuario {

    public Usuario ejecutar(ComandoAutenticacion comando) {
        return new Usuario(comando.getIdObjeto(), comando.getNombre(), comando.getNombreCompleto(),
                comando.getCorreoElectronico(), comando.getTokenDeAcceso());
    }
}
