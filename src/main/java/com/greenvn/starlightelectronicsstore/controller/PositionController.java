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
import com.greenvn.starlightelectronicsstore.service.PositionService;

@Controller
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@GetMapping("/admin/positions")
	public String showPositionList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "positionID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Position> pagePositon = positionService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Position> positions = pagePositon.getContent();
		if(positions.size() == 0) positions = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pagePositon.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("positions",positions);
		return "position-management";
	}
	
	@GetMapping("/admin/formAddPosition")
	public String addPositionForm(Position position) {
		return "position-add";
	}
	
	@PostMapping("/admin/addPosition")
	public String addPosition(@Valid Position position, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "position-add";
		}
		if(positionService.findPositionByName(position.getName()) != null)
		{
			model.addAttribute("messages","Chức vụ đã tồn tại!");
			return "position-add";
		}
		else model.addAttribute("messages",null);
		
		if(position.getEditData() == null) position.setEditData(false);
		
		positionService.addPosition(position);
		return "redirect:/admin/positions";
	}
	
	@GetMapping("/admin/formUpdatePosition")
	public String updatePositionForm(@RequestParam(name = "positionID")Long positionID, Model model) {
		Position position =  positionService.findPositionById(positionID);
		model.addAttribute("position", position);
		return "position-update";
	}

	@PostMapping("/admin/updatePosition")
	public String updatePosition(@RequestParam(name = "positionID")Long positionID,@Valid Position position, BindingResult result, Model model) {
		if(result.hasErrors()) {
			position.setPositionID(positionID);
			return "position-update";
		}

		Position P = positionService.findPositionByName(position.getName());
		if(P != null && P.getPositionID() != position.getPositionID())
		{
			model.addAttribute("messages","Chức vụ đã tồn tại!");
			return "position-update";
		}
		else model.addAttribute("messages",null);

		if(position.getEditData() == null) position.setEditData(false);
		
		positionService.updatePosition(position,positionID);
		return "redirect:/admin/positions";
	}
	
	@GetMapping("/admin/deletePosition")
	public String deletePosition(@RequestParam(name = "positionID")Long positionID, Model model) {
		positionService.deletePosition(positionID);
		return "redirect:/admin/positions";
		}
	 
}
