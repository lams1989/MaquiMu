package com.maquimu.dominio.alquiler.puerto.repositorio;

import com.maquimu.dominio.alquiler.modelo.Alquiler;

/**
 * Puerto de escritura para Alquiler (Repositorio). Define operaciones que
 * modifican datos.
 */
public interface AlquilerRepositorio {

	/**
	 * Guarda un nuevo alquiler o actualiza uno existente.
	 * 
	 * @param alquiler Alquiler a guardar
	 * @return Alquiler guardado con ID generado
	 */
	Alquiler guardar(Alquiler alquiler);

	/**
	 * Elimina un alquiler por su ID.
	 * 
	 * @param id ID del alquiler a eliminar
	 */
	void eliminar(Long id);
}
