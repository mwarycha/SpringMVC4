package com.twitter.TwitterEduApp.configurations;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * Created by emawary on 2018-03-21.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("searches")));
        return simpleCacheManager;
    }

    // biblioteka GUAVA
    // dane unieważniane są po 10 minutach. Wszystkie dane dodatkowo zostaną jeżeli w maszynie JVM zabraknie pamięci.
    @Bean
    public CacheManager cacheManager2() {
        GuavaCacheManager cacheManager = new GuavaCacheManager("searches");
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder()
                                .softValues()
                                .expireAfterWrite(10, TimeUnit.MINUTES)
                );
        return cacheManager;
    }
}
