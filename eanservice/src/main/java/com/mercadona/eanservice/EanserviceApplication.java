package com.mercadona.eanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mercadona.eanservice.repository")
@ComponentScan(basePackages = { "com.mercadona.eanservice.*" })
@EntityScan("com.mercadona.eanservice.model")
public class EanserviceApplication {

	public static void main(String[] args) {
        SpringApplication.run(EanserviceApplication.class, args);
    }

}
