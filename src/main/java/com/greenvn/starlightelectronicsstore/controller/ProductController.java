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

import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
import com.greenvn.starlightelectronicsstore.service.ProductAttributeService;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/products")
	public String showProductList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Product> pageProduct = productService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Product> products = pageProduct.getContent();
		if(products.size() == 0) products = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageProduct.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("products",products);
		return "product-management";
	}
	
	@GetMapping("/formAddProduct")
	public String addProductForm(Product product,Model model) {
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", imageService.getImages());
		return "product-add";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid Product product, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", imageService.getImages());
			return "product-add";
		}
		
		if(productService.findProductByName(product.getProductName()) != null)
		{
			model.addAttribute("messages", "Sản phẩm đã tồn tại!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", imageService.getImages());
			return "product-add";
		}
		else model.addAttribute("messages", null);
		productService.addProduct(product);
		
		Product newProduct = productService.findProductByName(product.getProductName());
		return "redirect:/products";
		//return attributesForProduct(newProduct.getProductID(),model);
	}
	
	@GetMapping("/formAttributesForProduct")
	public String attributesForProduct(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", imageService.getImages());
		model.addAttribute("productAttributes", productAttributeService.findProductAttributeByCategoryID(product.getCategory().getCategoryID()));
		model.addAttribute("product", product);
		return "product-addAttribute";
	}
	
	@PostMapping("/attributesForProduct")
	public String attributesForProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model) {
		productService.updateProduct(product, productID);
		return "redirect:/products";
	}
	
	@GetMapping("/formUpdateProduct")
	public String updateProductForm(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		model.addAttribute("product", product);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("productAttributes", productAttributeService.getProductAttributes());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", imageService.getImages());
		return "product-update";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model){
		if(result.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("productAttributes", productAttributeService.getProductAttributes());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", imageService.getImages());
			return "product-update";
		}
		
		Product P = productService.findProductByName(product.getProductName());
		if(P != null && P.getProductID() != product.getProductID()) {
			model.addAttribute("messages", "Sản phẩm đã tồn tại!");
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("productAttributes", productAttributeService.getProductAttributes());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", imageService.getImages());
			return "product-update";
		}
			
		else model.addAttribute("messages", null);
		
		productService.updateProduct(product, productID);
		return "redirect:/products";
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam(name = "productID")Long productID, Model model) {
		productService.deleteProduct(productID);
		return "redirect:/products";
	}
}
