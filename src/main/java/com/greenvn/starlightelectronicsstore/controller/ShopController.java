package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class ShopController {
	@Autowired
	private ProductService productService;

	@GetMapping("/shop")
	public String home(Model model) {
		List<Product> products = this.productService.getProducts();
		model.addAttribute("products", products);
		return "shop/home";
	}

	@RequestMapping(value = "/shop/productInfo", method = RequestMethod.GET)
	public String getProduct(Long productId, HttpServletRequest request, Model model) {

		Product product = productService.findProductById(productId);
		model.addAttribute("product", product);
		return "shop/product-detail";
	}
}
