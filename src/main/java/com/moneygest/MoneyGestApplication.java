package com.moneygest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.moneygest")
@EnableJpaRepositories(basePackages = "com.moneygest.Repository")
@EntityScan(basePackages = "com.moneygest.Model")
public class MoneyGestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyGestApplication.class, args);
	}
}
