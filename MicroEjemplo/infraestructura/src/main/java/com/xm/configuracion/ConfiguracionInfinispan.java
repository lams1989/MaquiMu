package com.xm.configuracion;

import java.time.Duration;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class ConfiguracionInfinispan {

	private static final String ROLESCACHE = "rolesCache";

	@Value("${infinispan.embedded.rolesTTL:1d}")
	private Duration rolesTTL;

	@Autowired
	private EmbeddedCacheManager cacheManager;

	@PostConstruct
	public void configurarCachesProyecto() {
		ConfigurationBuilder ispnConfig = new ConfigurationBuilder();
		ispnConfig.clustering().cacheMode(CacheMode.LOCAL);
		ispnConfig.expiration().lifespan(rolesTTL.toMillis());

		cacheManager.defineConfiguration(ROLESCACHE, ispnConfig.build());
	}
}