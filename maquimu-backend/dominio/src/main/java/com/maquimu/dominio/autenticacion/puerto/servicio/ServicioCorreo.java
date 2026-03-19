package com.maquimu.dominio.autenticacion.puerto.servicio;

public interface ServicioCorreo {
    void enviarPasswordTemporal(String destinatario, String nombreUsuario, String passwordTemporal);
}
