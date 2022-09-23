package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Employee;
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
}
	
