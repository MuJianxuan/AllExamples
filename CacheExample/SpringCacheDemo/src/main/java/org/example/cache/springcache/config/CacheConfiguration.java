package org.example.cache.springcache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author create 2022/8/17 by rao
 */
@Configuration
public class CacheConfiguration {

    public void cache(){

    }

    public static final Map<String, Cache> CACHE_MAP = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        com.github.benmanes.caffeine.cache.Cache<Object, Object> build = Caffeine.newBuilder().build();
        CACHE_MAP.computeIfAbsent("dd", k -> new CaffeineCache(k,build));
    }

}
