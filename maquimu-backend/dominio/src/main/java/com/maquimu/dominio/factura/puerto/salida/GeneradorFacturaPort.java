package com.maquimu.dominio.factura.puerto.salida;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.modelo.Maquinaria;

/**
 * Puerto de salida para la generación de documentos PDF de factura.
 * El adaptador de infraestructura implementará este puerto con la librería concreta (OpenPDF).
 */
public interface GeneradorFacturaPort {
    byte[] generarFacturaPdf(Factura factura, Alquiler alquiler, Cliente cliente, Maquinaria maquinaria);
}
