package ru.gb.shopbackendapiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.gb.persist")
public class ShopBackendApiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBackendApiAppApplication.class, args);
	}

}
