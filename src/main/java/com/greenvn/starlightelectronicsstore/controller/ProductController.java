package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public String showProductList(Model model)
	{
		model.addAttribute("products",productService.getProducts());
		return "/products";
	}
	
	@GetMapping("/formAddProduct")
	public String addProductForm(Product product) {
		return "add-product";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid Product product, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-product";
		}
		productService.addProduct(product);
		return "";
	}
	
	@GetMapping("/formUpdateProduct")
	public String updateProductForm(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = productService.findProductById(productID);
		model.addAttribute("product", product);
		return "update-product";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model){
		if(result.hasErrors()) {
			product.setProductID(productID);
			return "update-product";
		}
		productService.updateProduct(product, productID);
		return "";
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam(name = "productID")Long productID, Model model) {
		productService.deleteProduct(productID);
		return "";
	}
}
