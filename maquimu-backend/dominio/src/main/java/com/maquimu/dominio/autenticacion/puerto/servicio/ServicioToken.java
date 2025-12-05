package com.maquimu.dominio.autenticacion.puerto.servicio;

import com.maquimu.dominio.autenticacion.modelo.Usuario;

public interface ServicioToken {
    String generarToken(Usuario usuario);
}
