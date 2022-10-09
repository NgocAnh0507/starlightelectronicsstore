package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class ShopController {
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String home(Model model) {
		List<Product> products = this.productService.getProducts();
		model.addAttribute("products", products);
		return "shop/home";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "admin";
	}
	
	@GetMapping("/shop/productInfo")
	public String getProduct(@RequestParam(name = "productID")Long productID, HttpServletRequest request, Model model) {

		Product product = productService.findProductById(productID);
		model.addAttribute("images", product.getImages());
		model.addAttribute("product", product);
		return "shop/product-detail";
	}
}
