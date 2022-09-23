package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.service.AttributeTypeService;

@Controller
public class AttributeTypeController {
	
	@Autowired
	private AttributeTypeService attributeTypeService;

	@GetMapping("/attributeTypes")
	public String showAttributeTypeList(Model model)
	{
		model.addAttribute("attributeTypes",attributeTypeService.getAttributeTypes());
		return "attributeTypes";
				
	}
	
	@GetMapping("/formAddAttributeType")
	public String addAttributeTypeForm(AttributeType attributeType) {
		return "add-attributeType";
	}
	
	@PostMapping("/addAttributeType")
	public String addAttributeType(@Valid AttributeType attributeType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-attributeType";
		}
		attributeTypeService.addAttributeType(attributeType);
		return "";
	}

	@GetMapping("/formUpdateAttributeType")
	public String updateAttributeTypeForm(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model) {
		AttributeType attributeType =  attributeTypeService.findAttributeTypeById(attributeTypeID);
		model.addAttribute("attributeType", attributeType);
		return "update-attributeType";
	}

	@PostMapping("/updateAttributeType")
	public String updateAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID,@Valid AttributeType attributeType, BindingResult result, Model model){
		if(result.hasErrors()) {
			attributeType.setAttributeTypeID(attributeTypeID);
			return "update-attributeType";
		}
		attributeTypeService.updateAttributeType(attributeType,attributeTypeID);
		return "";
	}

	@GetMapping("/deleteAttributeType")
	public String deleteAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model) {
		attributeTypeService.deleteAttributeType(attributeTypeID);
		return "";
	}
}
