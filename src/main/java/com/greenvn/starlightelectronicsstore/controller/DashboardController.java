package com.greenvn.starlightelectronicsstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	@GetMapping("/admin")
	public String showIndexAdmin(){
		return "admin";
	}
}
