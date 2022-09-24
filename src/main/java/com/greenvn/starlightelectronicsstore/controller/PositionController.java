package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.service.PositionService;

@Controller
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@GetMapping("/positons")
	public String showPositionList(Model model)
	{
//		model.addAttribute("positions",positionService.getPositions());
		return "positions";
	}
	
	@GetMapping("/formAddPosition")
	public String addPositionForm(Position position) {
		return "add-position";
	}
	
	@PostMapping("/addPosition")
	public String addPosition(@Valid Position position, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-position";
		}
		positionService.addPosition(position);
		return "";
	}
	
	@GetMapping("/formUpdatePosition")
	public String updatePositionForm(@RequestParam(name = "positionID")Long positionID, Model model) {
		Position position =  positionService.findPositionById(positionID);
		model.addAttribute("position", position);
		return "update-position";
	}

	@PostMapping("/updatePosition")
	public String updatePosition(@RequestParam(name = "positionID")Long positionID,@Valid Position position, BindingResult result, Model model) {
		if(result.hasErrors()) {
			position.setPositionID(positionID);
			return "update-position";
		}
		positionService.updatePosition(position,positionID);
		return "";
	}
	
	@GetMapping("/deletePosition")
	public String deletePosition(@RequestParam(name = "positionID")Long positionID, Model model) {
		positionService.deletePosition(positionID);
		return "";	}
	 
}
