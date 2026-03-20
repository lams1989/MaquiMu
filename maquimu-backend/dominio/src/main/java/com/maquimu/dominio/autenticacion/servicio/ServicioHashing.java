package com.maquimu.dominio.autenticacion.servicio;

public interface ServicioHashing {
	String hashear(String textoPlano);

	boolean coinciden(String textoPlano, String hash);
}
