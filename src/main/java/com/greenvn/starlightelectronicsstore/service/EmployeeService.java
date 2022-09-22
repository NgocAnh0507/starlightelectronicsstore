package com.greenvn.starlightelectronicsstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Permission;
import com.greenvn.starlightelectronicsstore.repository.EmployeeRepository;

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
	
	public Employee findEmployeeById(Long id)
	{
		return employeeRepository.findById(id).get();
	}
	
	public Employee updateEmployee(Employee employeeNew, Long id)
	{
		Employee employee = findEmployeeById(id);
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
	
	public void deleteEmployee(Long id)
	{
		employeeRepository.deleteById(id);
	}
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final String DEFAULT_INITIAL_PASSWORD = "admin";
	
	public void createDefaultAdmin() throws Exception{
		//create all groups and permissions
		String password = passwordEncoder.encode("123456");
		// creation of the super admin admin:admin)
		// tao permisson
		Permission adminPermission = new Permission();
		adminPermission.setName("ADMIN");
		List<Permission> permissions = new ArrayList();
		permissions.add(adminPermission);
		Employee emp= new Employee();
		emp.setEmail("haivuong25*@gmail.com");
		emp.setBithYear(1996);
		emp.setIsActive(true);
		emp.setName("Hai");
		emp.setPassword(password);
		emp.setPermissions(permissions);
		emp.setIdEmployee(123456789);
		emp.setPhoneNumber("090999999");
		emp.setPosition(null);
		emp.setUserName("admin");
		employeeRepository.save(emp);
	}
}
	
