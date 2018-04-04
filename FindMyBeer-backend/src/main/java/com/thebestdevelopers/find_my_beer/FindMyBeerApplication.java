package com.thebestdevelopers.find_my_beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FindMyBeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindMyBeerApplication.class, args);
	}
}
