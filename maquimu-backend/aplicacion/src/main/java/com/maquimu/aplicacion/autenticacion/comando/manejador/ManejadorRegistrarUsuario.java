package com.maquimu.aplicacion.autenticacion.comando.manejador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.fabrica.FabricaUsuario;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorRegistrarUsuario {

    private final UsuarioDao usuarioDao;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final FabricaUsuario fabricaUsuario;
    private final FabricaCliente fabricaCliente;
    private final ServicioHashing servicioHashing;

    @Transactional
    public void ejecutar(ComandoRegistrarUsuario comando) {
        String nombre = comando.getNombreNormalizado();
        String apellido = comando.getApellidoNormalizado();

        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El nombre y apellido son requeridos");
        }

        // 1. Validar email único
        if (usuarioDao.existePorEmail(comando.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + comando.getEmail());
        }

        // 2. Validar identificación si el rol es CLIENTE
        if (comando.getRol() == RolUsuario.CLIENTE) {
            if (comando.getIdentificacion() == null || comando.getIdentificacion().isBlank()) {
                throw new IllegalArgumentException("La identificación es requerida para usuarios con rol CLIENTE");
            }
        }

        // 3. Hashear password
        String passwordHash = servicioHashing.hashear(comando.getPassword());

        // 4. Crear usuario usando fábrica
        Usuario usuario = fabricaUsuario.crear(comando, passwordHash);

        // 5. Persistir usuario
        Usuario usuarioGuardado = usuarioRepositorio.guardar(usuario);

        // 6. Si el rol es CLIENTE, crear automáticamente el registro en la tabla clientes
        if (comando.getRol() == RolUsuario.CLIENTE) {
            Cliente cliente = fabricaCliente.crearDesdeUsuario(usuarioGuardado, comando.getIdentificacion(), nombre, apellido);
            clienteRepositorio.guardar(cliente);
        }
    }
}
