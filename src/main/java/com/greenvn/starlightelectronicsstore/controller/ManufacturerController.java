package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;

@Controller
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;

	@GetMapping("/manufacturers")
	public String showManufacturerList(Model model)
	{
		model.addAttribute("manufacturers",manufacturerService.getManufacturers());
				return "manufacturers";
				
	}
	
	@GetMapping("/formAddManufacturer")
	public String addManufacturerForm(Manufacturer manufacturer) {
		return "add-manufacturer";
	}
	
	@PostMapping("/addManufacturer")
	public String addManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-manufacturer";
		}
		manufacturerService.addManufacturer(manufacturer);
		return "";
	}

	@GetMapping("/formUpdateManufacturer")
	public String updateManufacturerForm(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		Manufacturer manufacturer =  manufacturerService.findManufacturertById(manufacturerID);
		model.addAttribute("manufacturer", manufacturer);
		return "update-manufacturer";
	}

	@PostMapping("/updateManufacturer")
	public String updateManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID,@Valid Manufacturer manufacturer, BindingResult result, Model model){
		if(result.hasErrors()) {
			manufacturer.setManufacturerID(manufacturerID);
			return "update-manufacturer";
		}
		manufacturerService.updateManufacturer(manufacturer,manufacturerID);
		return "";
	}

	@GetMapping("/deleteManufacturer")
	public String deleteManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		manufacturerService.deleteManufacturer(manufacturerID);
		return "";
	}
}

