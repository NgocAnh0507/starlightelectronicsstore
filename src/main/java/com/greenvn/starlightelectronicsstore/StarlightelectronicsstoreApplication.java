package com.greenvn.starlightelectronicsstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.greenvn.starlightelectronicsstore.service.EmployeeService;


@SpringBootApplication
public class StarlightelectronicsstoreApplication implements CommandLineRunner {

	@Autowired
	private EmployeeService empSer;
	public static void main(String[] args) {
		SpringApplication.run(StarlightelectronicsstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Test");
		if(empSer.getEmployeeByUserName("admin")==null) {
			empSer.createDefaultAdmin();
		}
	}
}
