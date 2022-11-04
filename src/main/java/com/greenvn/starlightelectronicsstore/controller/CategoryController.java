package com.greenvn.starlightelectronicsstore.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.service.CategoryService;


@Controller
@RequestMapping(value = "/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String showCategoryList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "categoryID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model, HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<Category> pageCategory = categoryService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Category> categories = pageCategory.getContent();
		if(categories.size() == 0) categories = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageCategory.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected", "categories");
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("categories",categories);
		return "category-management";
	}
	
	@GetMapping("/formAddCategory")
	public String addCategoryForm(Category category, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		model.addAttribute("callFrom", callFrom);
		model.addAttribute("callFromID", callFromID);
		return "category-add";
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@Valid Category category, BindingResult result, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		if (result.hasErrors()) {
			model.addAttribute("notice", "Thêm danh mục thất bại");
			return "category-add";
		}
		
		if(categoryService.findCategoryByName(category.getName()) != null) {
			
			model.addAttribute("messages","Danh mục đã tồn tại!");
			return "category-add";
		}
		else model.addAttribute("messages",null);
		
		categoryService.addCategory(category);
		
		String notice = "Thêm danh mục thành công!";
		if(callFromID != null) return "redirect:" + callFrom + callFromID + ", notice=" + notice;
		return "redirect:" + callFrom + "?notice=" + notice;
	}
	
	@GetMapping("/formUpdateCategory")
	public String updateCategoryForm(@RequestParam(name = "categoryID")Long categoryID, Model model) {
		Category category =  categoryService.findCategoryById(categoryID);
		model.addAttribute("category", category);
		return "category-update";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@RequestParam(name = "categoryID")Long categoryID,@Valid Category category, BindingResult result, Model model) {
		if(result.hasErrors()) {
			category.setCategoryID(categoryID);
			return "category-update";
		}
		Category C = categoryService.findCategoryByName(category.getName());
		if(C != null && C.getCategoryID() != category.getCategoryID()) {
			model.addAttribute("messages","Danh mục đã tồn tại!");
			return "category-update";
		}
		
		else model.addAttribute("messages",null);
		
		categoryService.updateCategory(category,categoryID);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/deleteCategory")
	public String deleteCategory(@RequestParam(name = "categoryID")Long categoryID, Model model,HttpServletRequest request) {
		Category category =  categoryService.findCategoryById(categoryID);
		if(category.getProductAttributes().size() > 0 || category.getProducts().size() > 0) {
			model.addAttribute("messages","Không thể xóa danh mục đang có thuộc tính hoặc sản phẩm!");
			return showCategoryList(1,"categoryID","asc",model,request,null);
		}
		categoryService.deleteCategory(categoryID);
		return "redirect:/admin/categories";
	}
	 
}