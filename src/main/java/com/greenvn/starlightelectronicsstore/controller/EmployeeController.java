package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public String showEmployeeList(Model model)
	{
		model.addAttribute("employees",employeeService.getEmployees());
		return "/employees";
	}
	
	@GetMapping("/formAddEmployee")
	public String addEmployeeForm(Employee employee) {
		return "add-employee";
	}
	
	@PostMapping("/addEmployee")
	public String addEmployee(@Valid Employee employee, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-employee";
		}
		employeeService.addEmployee(employee);
		return "";
	}
	
	@GetMapping("/admin/formUpdateEmployee")
	public String updateEmployeeForm(@RequestParam(name = "employeeID")Long employeeID, Model model) {
		Employee employee = employeeService.findEmployeeById(employeeID);
		model.addAttribute("employee", employee);
		return "update-employee";
	}
	
	@PostMapping("admin/updateEmployee")
	public String updateEmployee(@RequestParam(name = "employeeID")Long employeeID,@Valid Employee employee, BindingResult result, Model model){
		if(result.hasErrors()) {
			employee.setEmployeeID(employeeID);
			return "update-employee";
		}
		employeeService.updateEmployee(employee, employeeID);
		return "redirect:/admin/infoUser";
	}
	
	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam(name = "employeeID")Long employeeID, Model model) {
		employeeService.deleteEmployee(employeeID);
		return "";
	}
	
}

