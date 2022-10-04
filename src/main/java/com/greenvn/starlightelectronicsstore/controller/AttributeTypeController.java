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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.service.AttributeTypeService;

@Controller
@RequestMapping(value = "/admin")
public class AttributeTypeController {
	
	@Autowired
	private AttributeTypeService attributeTypeService;

	@GetMapping("/attributeTypes")
	public String showAttributeTypeList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "attributeTypeID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<AttributeType> pageAttributeType = attributeTypeService.findAll(pageNo, pageSize,sortField,sortDir);
		List<AttributeType> attributeTypes = pageAttributeType.getContent();
		if(attributeTypes.size() == 0) attributeTypes = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageAttributeType.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("attributeTypes",attributeTypes);
		return "attributeType-management";
				
	}
	
	@GetMapping("/formAddAttributeType")
	public String addAttributeTypeForm(AttributeType attributeType) {
		return "attributeType-add";
	}
	
	@PostMapping("/addAttributeType")
	public String addAttributeType(@Valid AttributeType attributeType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "attributeType-add";
		}
		
		if(attributeTypeService.findAttributeTypeByName(attributeType.getName()) != null) {
			
			model.addAttribute("messages","Loại thuộc tính đã tồn tại!");
			return "attributeType-add";
		}
		else model.addAttribute("messages",null);
		
		attributeTypeService.addAttributeType(attributeType);
		return "redirect:/admin/attributeTypes";
	}

	@GetMapping("/formUpdateAttributeType")
	public String updateAttributeTypeForm(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model) {
		AttributeType attributeType =  attributeTypeService.findAttributeTypeById(attributeTypeID);
		model.addAttribute("attributeType", attributeType);
		return "attributeType-update";
	}

	@PostMapping("/updateAttributeType")
	public String updateAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID,@Valid AttributeType attributeType, BindingResult result, Model model){
		if(result.hasErrors()) {
			attributeType.setAttributeTypeID(attributeTypeID);
			return "attributeType-update";
		}

		if(attributeTypeService.findAttributeTypeByName(attributeType.getName()) != null) {
			
			model.addAttribute("messages","Loại thuộc tính đã tồn tại!");
			return "attributeType-update";
		}
		else model.addAttribute("messages",null);
		
		attributeTypeService.updateAttributeType(attributeType,attributeTypeID);
		return "redirect:/admin/attributeTypes";
	}

	@GetMapping("/deleteAttributeType")
	public String deleteAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model) {
		attributeTypeService.deleteAttributeType(attributeTypeID);
		showAttributeTypeList(1,"attributeTypeID","asc",model);
		return "redirect:/admin/attributeTypes";
	}
}
