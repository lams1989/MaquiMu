package com.xm.configuracion;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionCache {

	@Bean
	public EmbeddedCacheManager embeddedCacheManager() {
		return new DefaultCacheManager();
	}

}
