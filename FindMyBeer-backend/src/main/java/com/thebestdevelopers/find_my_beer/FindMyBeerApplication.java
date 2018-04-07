package com.thebestdevelopers.find_my_beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("com.thebestdevelopers.find_my_beer.repository")/*
@EntityScan("com.thebestdevelopers.find_my_beer.model")
@ComponentScan("com.thebestdevelopers.find_my_beer")*/
public class FindMyBeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindMyBeerApplication.class, args);
	}
}
