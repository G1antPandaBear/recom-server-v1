package com.pandabear.recom.global.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        return new EhCacheManagerFactoryBean();
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        CacheConfiguration codeCacheConfig = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(0)
                .timeToLiveSeconds(1800)     // half-hour
                .maxEntriesLocalHeap(0)
                .memoryStoreEvictionPolicy("LRU")
                .name("codeCaching");

        Cache codeCache = new Cache(codeCacheConfig);
        Objects.requireNonNull(ehCacheManagerFactoryBean().getObject())
                .addCache(codeCache);
        return new EhCacheCacheManager(
                Objects.requireNonNull(ehCacheManagerFactoryBean().getObject())
        );
    }

}
