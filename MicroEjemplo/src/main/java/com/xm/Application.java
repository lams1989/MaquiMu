package com.xm;

import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.xm.clavesecreto.modelo.enums.ClaveSecretoEnum;
import com.xm.infraestructura.configuracion.EnvironmentContextInitializer;

@ComponentScan({ "com.xm" })
@SpringBootApplication
@EnableCaching
public class Application {
	private static final Set<String> PERFILES_DESARROLLO = Set.of("dev", "local");
	private static final String PERFILES_ACTIVOS_DE_SPRING = "SPRING_PROFILES_ACTIVE";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		String perfilActivoDeSpring = System.getenv(PERFILES_ACTIVOS_DE_SPRING);
		if (perfilActivoDeSpring == null || !PERFILES_DESARROLLO.contains(perfilActivoDeSpring)) {
			ApplicationInsights.attach();
		}

		app.addInitializers(new EnvironmentContextInitializer(ClaveSecretoEnum.class));
		app.run(args);
	}
}
