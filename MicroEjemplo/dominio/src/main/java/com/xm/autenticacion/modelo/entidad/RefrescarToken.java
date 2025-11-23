package com.xm.autenticacion.modelo.entidad;

import com.xm.dominio.ValidadorArgumento;

import lombok.Getter;

@Getter
public class RefrescarToken {


    private static final String EL_CAMPO_TOKEN_DE_ACCESO_ES_REQUERIDO = "El campo tokenDeAcceso es requerido.";
    private static final String EL_CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO = "El campo tokenDeAcceso no puede ser vacío.";
    private static final String EL_CAMPO_TOKEN_DE_REFRESCO_ES_REQUERIDO = "El campo tokenDeRefresco es requerido.";
    private static final String EL_CAMPO_TOKEN_DE_REFRESCO_NO_PUEDE_SER_VACIO = "El campo tokenDeRefresco no puede ser vacío.";
    private String tokenDeAcceso;
    private String tokenDeRefresco;

    public RefrescarToken(String tokenDeAcceso, String tokenDeRefresco) {

        ValidadorArgumento.validarObligatorio(tokenDeAcceso, EL_CAMPO_TOKEN_DE_ACCESO_ES_REQUERIDO);
        ValidadorArgumento.validarVacio(tokenDeAcceso, EL_CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO);
        ValidadorArgumento.validarObligatorio(tokenDeRefresco, EL_CAMPO_TOKEN_DE_REFRESCO_ES_REQUERIDO);
        ValidadorArgumento.validarVacio(tokenDeRefresco, EL_CAMPO_TOKEN_DE_REFRESCO_NO_PUEDE_SER_VACIO);

        this.tokenDeAcceso = tokenDeAcceso;
        this.tokenDeRefresco = tokenDeRefresco;
    }
}
