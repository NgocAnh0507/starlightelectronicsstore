package com.greenvn.starlightelectronicsstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.repository.PositionRepository;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;
import com.greenvn.starlightelectronicsstore.service.PositionService;


@SpringBootApplication
public class StarlightelectronicsstoreApplication implements CommandLineRunner {

	@Autowired
	private EmployeeService empSer;
	@Autowired
	private PositionService posSer;
	
	public static void main(String[] args) {
		SpringApplication.run(StarlightelectronicsstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Test");
		if(empSer.findEmployeeByUserName("Admin")==null) {

			if(posSer.findPositionByUserName("Quản trị viên")==null) {
				Position adminPositon = new Position();
				adminPositon.setName("Quản trị viên");
				adminPositon.setRole("ADMIN");
				posSer.addPosition(adminPositon);
			}
			empSer.createDefaultAdmin();
		}
	}
}
