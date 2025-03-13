package com.bureauworks.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.bureauworks.translator.entities")
@EnableJpaRepositories(basePackages = "com.bureauworks.translator.repositories")
public class TranslatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TranslatorApplication.class, args);
    }
}