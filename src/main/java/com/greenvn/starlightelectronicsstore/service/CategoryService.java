package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getCategories()
	{
		return categoryRepository.findAll();
	}
	
	public Category addCategory(Category category)
	{
		Category categorySaved = categoryRepository.saveAndFlush(category);
		return categorySaved;
	}
	
	public Category findCategoryById(Long id)
	{
		return categoryRepository.findById(id).get();
	}
	
	public Category updateCategory(Category categoryNew, Long id)
	{
		Category category = findCategoryById(id);
		category.setName(categoryNew.getName());
		return categoryRepository.save(category);
	}
	
	public void deleteCategory(Long id)
	{
		categoryRepository.deleteById(id);
	}
}
