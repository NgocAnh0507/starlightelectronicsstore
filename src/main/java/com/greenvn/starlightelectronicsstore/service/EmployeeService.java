package com.greenvn.starlightelectronicsstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getEmployees()
	{
		return employeeRepository.findAll();
	}
	
	public Employee addEmployee(Employee employee)
	{
		Employee employeeSaved = employeeRepository.save(employee);
		return employeeSaved;
	}
	
	public Employee findEmployeeById(Long employeeID)
	{
		return employeeRepository.findById(employeeID).get();
	}
	
	public Employee updateEmployee(Employee employeeNew, Long employeeID)
	{
		Employee employee = findEmployeeById(employeeID);
		employee.setUserName(employeeNew.getUserName());
		employee.setPassword(employeeNew.getPassword());
		employee.setIsActive(employeeNew.getIsActive());
		employee.setPosition(employeeNew.getPosition());
		employee.setBithYear(employeeNew.getBithYear());
		employee.setEmail(employeeNew.getEmail());
		employee.setName(employeeNew.getName());
		employee.setPhoneNumber(employeeNew.getPhoneNumber());
		return employeeRepository.save(employee);
	}
	
	public Employee getEmployeeByUserName(String username)
	{
		return employeeRepository.findEmployeeByUserName(username);
	}
	
	public void deleteEmployee(Long employeeID)
	{
		employeeRepository.deleteById(employeeID);
	}
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private static final String DEFAULT_INITIAL_PASSWORD = "admin";
	public void createDefaultAdmin() throws Exception{
		String password = passwordEncoder.encode("123456");
		Position adminPositon = new Position();
		adminPositon.setName("ADMIN");
		adminPositon.setEditData(true);
		
		List<Position>positions = new ArrayList();
		positions.add(adminPositon);
		Employee emp = new Employee();
		emp.setBithYear(1996);
		emp.setEmail("haivuong258@gmail.com");
		emp.setIsActive(true);
		emp.setName("Hai");
		emp.setPassword(password);
		emp.setPhoneNumber("123456789");
		emp.setPosition(adminPositon);
		emp.setUserName("admin");
		employeeRepository.save(emp);
	}
	@GetMapping("/admin")
	public String showIndexAdmin(){
		return "admin";
	}
	
}
	
