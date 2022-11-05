package com.greenvn.starlightelectronicsstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String loginPage(String error,Model model) {
		model.addAttribute("error", error);
		return "login";
	}
}
