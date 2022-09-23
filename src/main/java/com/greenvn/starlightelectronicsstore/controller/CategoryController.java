package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String showCategoryList(Model model)
	{
		model.addAttribute("categories",categoryService.getCategories());
		return "categories";
	}
	
	@GetMapping("/formAddCategory")
	public String addCategoryForm(Category category) {
		return "add-category";
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@Valid Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}
		categoryService.addCategory(category);
		return "";
	}
	
	@GetMapping("/formUpdateCategory")
	public String updateCategoryForm(@RequestParam(name = "categoryID")Long categoryID, Model model) {
		Category category =  categoryService.findCategoryById(categoryID);
		model.addAttribute("category", category);
		return "update-category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@RequestParam(name = "categoryID")Long categoryID,@Valid Category category, BindingResult result, Model model) {
		if(result.hasErrors()) {
			category.setCategoryID(categoryID);
			return "update-category";
		}
		categoryService.updateCategory(category,categoryID);
		return "";
	}
	
	@GetMapping("/deleteCategory")
	public String deleteCategory(@RequestParam(name = "categoryID")Long categoryID, Model model) {
		categoryService.deleteCategory(categoryID);
		return "";	}
	 
}
