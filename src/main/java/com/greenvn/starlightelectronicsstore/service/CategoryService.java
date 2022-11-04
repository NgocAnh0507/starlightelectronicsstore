package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		Category categorySaved = categoryRepository.save(category);
		return categorySaved;
	}

	public Category findCategoryByName(String Name)
	{
		return categoryRepository.findCategoryByName(Name);
	}
	
	public Category findCategoryById(Long categoryID)
	{
		if(categoryRepository.findById(categoryID).isEmpty()) return null;
		return categoryRepository.findById(categoryID).get();
	}
	
	public Category updateCategory(Category categoryNew, Long categoryID)
	{
		Category category = findCategoryById(categoryID);
		if(category == null) return null;
		category.setName(categoryNew.getName());
		return categoryRepository.save(category);
	}
	
	public void deleteCategory(Long categoryID)
	{
		categoryRepository.deleteById(categoryID);
	}
	
	      //Pageable
	public Page<Category> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
			
			//sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
					Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
			
			Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
			Page<Category>pageCategory = categoryRepository.findAll(pageable);
			return pageCategory;
	}
}
