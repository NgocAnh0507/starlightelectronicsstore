package com.greenvn.starlightelectronicsstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;

@Controller
public class DashboardController {
	@Autowired
	private EmployeeService empSer;
	@GetMapping("/admin")
	public String showAdmin(){
		return "admin";
	}

	/*
	 * @GetMapping("/admin/employees") public String showEmployees(){ return
	 * "employee-management"; }
	 */
	/*
	 * @GetMapping("/admin/donhang") public String showDonHang(){ return
	 * "donhangList"; }
	 */
//	@GetMapping("/admin/products")
//	public String showProduct(){
//		return "product-management";
//	}
	@GetMapping("/admin/user")
	public String showUserList() {
		return "EmployeeList";
	}
	@GetMapping("/admin/infoUser")
	public String showUserInfoPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Employee emp = empSer.findEmployeeByUserName(userName);
		model.addAttribute("employee", emp);
		return "infoUSer";
	}
	
	
}
