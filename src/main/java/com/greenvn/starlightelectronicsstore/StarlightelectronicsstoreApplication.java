package com.greenvn.starlightelectronicsstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StarlightelectronicsstoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StarlightelectronicsstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Test");
	}
}
