package com.maquimu.dominio.autenticacion.puerto.servicio;

public interface ServicioHashing {
    String hashear(String textoPlano);
    boolean coinciden(String textoPlano, String hash);
}
