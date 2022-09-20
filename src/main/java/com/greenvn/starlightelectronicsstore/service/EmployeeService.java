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
	
	public List<Employee>getUser(){
		return employeeRepository.findAll();
	}
	public Employee getByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}
	public Employee addEmployee(Employee employee) {
		Employee employeeSaved = employeeRepository.save(employee);
		return employeeSaved;
	}
}
