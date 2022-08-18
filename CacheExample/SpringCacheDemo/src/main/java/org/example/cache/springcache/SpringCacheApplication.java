package org.example.cache.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: create 2022/8/15 by rao
 */
@EnableCaching
@SpringBootApplication
public class SpringCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCacheApplication.class, args);
    }
}