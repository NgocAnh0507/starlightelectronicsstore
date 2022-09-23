package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.ProductAttributeService;

@Controller
public class ProductAttributeController {
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@GetMapping("/productAttributes")
	public String showProductAttributeList(Model model)
	{
		model.addAttribute("productAttributes",productAttributeService.getProductAttributes());
		return "/productAttributes";
	}
	
	@GetMapping("/formAddProductAttribute")
	public String addProductAttributeForm(ProductAttribute productAttribute) {
		return "add-productAttribute";
	}
	
	@PostMapping("/addProductAttribute")
	public String addProductAttribute(@Valid ProductAttribute productAttribute, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-productAttribute";
		}
		productAttributeService.addProductAttribute(productAttribute);
		return "";
	}
	
	@GetMapping("/formUpdateProductAttribute")
	public String updateProductAttributeForm(@RequestParam(name = "productAttributeID")Long productAttributeID, Model model) {
		ProductAttribute productAttribute = productAttributeService.findProductAttributeById(productAttributeID);
		model.addAttribute("productAttribute", productAttribute);
		return "update-productAttribute";
	}
	
	@PostMapping("/updateProductAttribute")
	public String updateProductAttribute(@RequestParam(name = "productAttributeID")Long productAttributeID,@Valid ProductAttribute productAttribute, BindingResult result, Model model){
		if(result.hasErrors()) {
			productAttribute.setProductAttributeID(productAttributeID);
			return "update-productAttribute";
		}
		productAttributeService.updateProductAttribute(productAttribute, productAttributeID);
		return "";
	}
	
	@GetMapping("/deleteProductAttribute")
	public String deleteProductAttribute(@RequestParam(name = "productAttributeID")Long productAttributeID, Model model) {
		productAttributeService.deleteProductAttribute(productAttributeID);
		return "";
	}
}
