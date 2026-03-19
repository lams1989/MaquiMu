package com.maquimu.aplicacion.alquiler.consulta.manejador;

import com.maquimu.aplicacion.alquiler.consulta.ConsultaAlquileresCliente;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manejador de la consulta para obtener alquileres de un cliente.
 */
@Service
public class ManejadorAlquileresCliente {

    private final AlquilerDao alquilerDao;

    public ManejadorAlquileresCliente(AlquilerDao alquilerDao) {
        this.alquilerDao = alquilerDao;
    }

    public List<Alquiler> ejecutar(ConsultaAlquileresCliente consulta) {
        return alquilerDao.buscarPorCliente(consulta.getClienteId());
    }
}
