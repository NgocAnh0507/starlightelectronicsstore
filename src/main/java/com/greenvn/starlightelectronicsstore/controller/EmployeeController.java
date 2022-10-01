package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;
import com.greenvn.starlightelectronicsstore.service.PositionService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PositionService positionService;

	@GetMapping("/employees")
	public String showEmployeeList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "employeeID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Employee> pageEmployee = employeeService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Employee> employees = pageEmployee.getContent();
		if(employees.size() == 0) employees = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageEmployee.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("employees",employees);
		return "employee-management";
	}
	
	@GetMapping("/formAddEmployee")
	public String addEmployeeForm(Employee employee,Model model) {
		model.addAttribute("positions",positionService.getPositions());
		return "employee-add";
	}
	
	@PostMapping("/addEmployee")
	public String addEmployee(@Valid Employee employee, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		if(!employeeService.checkPhoneNumber(employee.getPhoneNumber()))
		{
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		else model.addAttribute("messages",null);
		
		employeeService.addEmployee(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/formUpdateEmployee")
	public String updateEmployeeForm(@RequestParam(name = "employeeID")Long employeeID, Model model) {
		Employee employee = employeeService.findEmployeeById(employeeID);
		model.addAttribute("employee", employee);
		model.addAttribute("positions",positionService.getPositions());
		return "employee-update";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(@RequestParam(name = "employeeID")Long employeeID,@Valid Employee employee, BindingResult result, Model model){
		if(result.hasErrors()) {
			Employee emp = employeeService.findEmployeeById(employeeID);
			model.addAttribute("employee", emp);
			model.addAttribute("positions",positionService.getPositions());
			return "employee-update";
		}
		if(!employeeService.checkPhoneNumber(employee.getPhoneNumber()))
		{
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			Employee emp = employeeService.findEmployeeById(employeeID);
			model.addAttribute("employee", emp);
			model.addAttribute("positions",positionService.getPositions());
			return "employee-update";
		}
		else model.addAttribute("messages",null);
		
		employeeService.updateEmployee(employee, employeeID);
		return "redirect:/employees";
	}
	
	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam(name = "employeeID")Long employeeID, Model model) {
		employeeService.deleteEmployee(employeeID);
		return "redirect:/employees";
	}
}

