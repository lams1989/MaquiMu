package com.maquimu.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.servicio.ServicioCalculadorCostoAlquiler;
import com.maquimu.dominio.alquiler.servicio.ServicioValidadorDisponibilidadMaquinaria;

/**
 * Configuración de beans para servicios de dominio de alquiler. Los servicios
 * de dominio no deben depender de Spring, así que los instanciamos aquí para
 * inyección de dependencias.
 */
@Configuration
public class AlquilerConfig {

	@Bean
	public ServicioCalculadorCostoAlquiler calculadorCostoAlquiler() {
		return new ServicioCalculadorCostoAlquiler();
	}

	@Bean
	public ServicioValidadorDisponibilidadMaquinaria validadorDisponibilidadMaquinaria(AlquilerDao alquilerDao) {
		return new ServicioValidadorDisponibilidadMaquinaria(alquilerDao);
	}
}
