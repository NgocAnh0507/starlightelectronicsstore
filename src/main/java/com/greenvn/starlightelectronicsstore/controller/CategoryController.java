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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.service.CategoryService;


@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String showCategoryList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "categoryID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Category> pageCategory = categoryService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Category> categories = pageCategory.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageCategory.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("categories",categoryService.getCategories());
		return "category-management";
	}
	
	@GetMapping("/formAddCategory")
	public ModelAndView addCategoryForm(Category category) {
		return new ModelAndView(new RedirectView("add-category"));
		
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@Valid Category category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-category";
		}
		categoryService.addCategory(category);
		return "category-management";
	}
	
	@GetMapping("/formUpdateCategory")
	public ModelAndView updateCategoryForm(@RequestParam(name = "categoryID")Long categoryID, Model model) {
		Category category =  categoryService.findCategoryById(categoryID);
		model.addAttribute("category", category);
		return new ModelAndView(new RedirectView("update-category"));
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@RequestParam(name = "categoryID")Long categoryID,@Valid Category category, BindingResult result, Model model) {
		if(result.hasErrors()) {
			category.setCategoryID(categoryID);
			return "update-category";
		}
		categoryService.updateCategory(category,categoryID);
		return "category-management";
	}
	
	@GetMapping("/deleteCategory")
	public String deleteCategory(@RequestParam(name = "categoryID")Long categoryID, Model model) {
		categoryService.deleteCategory(categoryID);
		//chỗ này pải thêm param
		//showCategoryList(model);
		return "category-management";	
		
	}
	 
}