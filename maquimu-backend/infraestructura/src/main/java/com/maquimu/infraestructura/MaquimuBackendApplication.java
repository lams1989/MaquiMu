package com.maquimu.infraestructura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.maquimu")
@EntityScan(basePackages = "com.maquimu.infraestructura.*.adaptador.entidad")
@EnableJpaRepositories(basePackages = "com.maquimu.infraestructura.*.adaptador.dao")
public class MaquimuBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaquimuBackendApplication.class, args);
    }
}
