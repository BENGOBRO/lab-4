package ru.bengo.animaltracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AnimalTrackingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(AnimalTrackingApplication.class, args);
		SpringApplication.exit(ctx, () -> 0);
	}

}
