package com.greenvn.starlightelectronicsstore.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.model.EmployeeInfo;
import com.greenvn.starlightelectronicsstore.repository.EmployeeRepository;
import com.greenvn.starlightelectronicsstore.repository.PositionRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PositionRepository positionRepository;
	
	public List<Employee> getEmployees()
	{
		return employeeRepository.findAll();
	}
	
	public Employee addEmployee(Employee employee)
	{
		
		String password = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(password);
		Employee employeeSaved = employeeRepository.save(employee);
		return employeeSaved;
	}
	
	public Employee findEmployeeById(Long employeeID)
	{
		if(employeeRepository.findById(employeeID).isEmpty()) return null;
		return employeeRepository.findById(employeeID).get();
	}
	
	public Employee updateEmployee(Employee employeeNew, Long employeeID)
	{
		Employee employee = findEmployeeById(employeeID);
		if(employee == null) return null;
		employee.setUserName(employeeNew.getUserName());
		employee.setPassword(passwordEncoder.encode(employeeNew.getPassword()));
		employee.setIsActive(employeeNew.getIsActive());
		employee.setPosition(employeeNew.getPosition());
		employee.setBirthday(employeeNew.getBirthday());
		employee.setEmail(employeeNew.getEmail());
		employee.setName(employeeNew.getName());
		employee.setPhoneNumber(employeeNew.getPhoneNumber());
		employee.setAvatar(employeeNew.getAvatar());
		return employeeRepository.save(employee);
	}
	
	public Employee updateEmployee(EmployeeInfo employeeNew, Long employeeID)
	{
		Employee employee = findEmployeeById(employeeID);

		if(employee == null) return null;
		
		employee.setBirthday(employeeNew.getBirthday());
		employee.setEmail(employeeNew.getEmail());
		employee.setName(employeeNew.getName());
		employee.setPhoneNumber(employeeNew.getPhoneNumber());
		return employeeRepository.save(employee);
	}
	
	public Employee findEmployeeByUserName(String username)
	{
		return employeeRepository.findEmployeeByUserName(username);
	}
	
	public void deleteEmployee(Long employeeID)
	{
		employeeRepository.deleteById(employeeID);
	}
	
	// Pageable
	public Page<Employee> findAll(int pageNo, int pageSize, String sortField, String sortDirection) {

		// sort
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		Page<Employee> pageEmployee = employeeRepository.findAll(pageable);
		return pageEmployee;
	}
	
	// Mã hóa password
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final String DEFAULT_INITIAL_PASSWORD = "admin";
	public void createDefaultAdmin() throws Exception{
		String password = passwordEncoder.encode("123456");
		Position adminPositon = positionRepository.findPositionByName("Quản trị viên");
		String sDate1="01/01/1996";  
	    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
	    
		List<Position> positions = new ArrayList<Position>();
		positions.add(adminPositon);
		Employee emp = new Employee();
		emp.setBirthday(date1);
		emp.setEmail("haivuong258@gmail.com");
		emp.setIsActive(true);
		emp.setName("Hai");
		emp.setUserName("admin");
		emp.setPassword(password);
		emp.setPhoneNumber("0123456789");
		emp.setPosition(adminPositon);
		employeeRepository.save(emp);
	}
	
	public boolean checkPhoneNumber(String numberPhone)
	{ 
		for(int i=0; i<numberPhone.length(); i++)
		{
			if(numberPhone.charAt(i) < '0' || numberPhone.charAt(i) > '9')
				return false;
		}
		return true;
	}
}
	
