package com.xm.autenticacion.modelo.entidad;

import com.xm.dominio.ValidadorArgumento;

import lombok.Getter;

@Getter
public class Usuario {
    private static final String CAMPO_ID_OBJETO_REQUERIDO = "El campo idObjeto es requerido.";
    private static final String CAMPO_NOMBRE_REQUERIDO = "El campo nombre es requerido.";
    private static final String CAMPO_NOMBRE_COMPLETO_REQUERIDO = "El nombreCompleto idObjeto es requerido.";
    private static final String CAMPO_CORREO_ELECTRONICO_REQUERIDO = "El campo correoElectronico es requerido.";
    private static final String CAMPO_TOKEN_DE_ACCESO_REQUERIDO = "El campo tokenDeAcceso es requerido.";
    private static final String CAMPO_ID_OBJETO_NO_PUEDE_SER_VACIO = "El campo idObjeto no puede ser vacío.";
    private static final String CAMPO_NOMBRE_NO_PUEDE_SER_VACIO = "El campo nombre no puede ser vacío.";
    private static final String CAMPO_NOMBRE_COMPLETO_NO_PUEDE_SER_VACIO = "El nombreCompleto idObjeto no puede ser vacío.";
    private static final String CAMPO_CORREO_ELECTRONICO_NO_PUEDE_SER_VACIO = "El campo correoElectronico no puede ser vacío.";
    private static final String CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO = "El campo tokenDeAcceso no puede ser vacío.";
    private String idObjeto;
    private String nombre;
    private String nombreCompleto;
    private String correoElectronico;
    private String tokenDeAcceso;

    public Usuario(String idObjeto, String nombre, String nombreCompleto, String correoElectronico, String tokenDeAcceso) {
        ValidadorArgumento.validarObligatorio(idObjeto, CAMPO_ID_OBJETO_REQUERIDO);
        ValidadorArgumento.validarObligatorio(nombre, CAMPO_NOMBRE_REQUERIDO);
        ValidadorArgumento.validarObligatorio(nombreCompleto, CAMPO_NOMBRE_COMPLETO_REQUERIDO);
        ValidadorArgumento.validarObligatorio(correoElectronico, CAMPO_CORREO_ELECTRONICO_REQUERIDO);
        ValidadorArgumento.validarObligatorio(tokenDeAcceso, CAMPO_TOKEN_DE_ACCESO_REQUERIDO);

        ValidadorArgumento.validarVacio(idObjeto, CAMPO_ID_OBJETO_NO_PUEDE_SER_VACIO);
        ValidadorArgumento.validarVacio(nombre, CAMPO_NOMBRE_NO_PUEDE_SER_VACIO);
        ValidadorArgumento.validarVacio(nombreCompleto, CAMPO_NOMBRE_COMPLETO_NO_PUEDE_SER_VACIO);
        ValidadorArgumento.validarVacio(correoElectronico, CAMPO_CORREO_ELECTRONICO_NO_PUEDE_SER_VACIO);
        ValidadorArgumento.validarVacio(tokenDeAcceso, CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO);

        this.idObjeto = idObjeto;
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.tokenDeAcceso = tokenDeAcceso;
    }
}
