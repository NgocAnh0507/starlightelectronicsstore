package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService proSer;
	@GetMapping("/")
	public String homePage(Model model) {
		List<Product>products = this.proSer.getProducts();
		model.addAttribute("products",products);
		return "shop/home";
	}
}
