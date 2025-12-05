package com.maquimu.infraestructura.autenticacion.adaptador.servicio;

import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BcryptServicioHashing implements ServicioHashing {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String hashear(String textoPlano) {
        return passwordEncoder.encode(textoPlano);
    }

    @Override
    public boolean coinciden(String textoPlano, String hash) {
        return passwordEncoder.matches(textoPlano, hash);
    }
}
