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
import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.service.PositionService;

@Controller
@RequestMapping(value = "/admin")
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@GetMapping("/positions")
	public String showPositionList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "positionID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model, HttpServletRequest request)
	{
		int pageSize = 9;
		Page<Position> pagePositon = positionService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Position> positions = pagePositon.getContent();
		if(positions.size() == 0) positions = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pagePositon.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","positions" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("positions",positions);
		return "position-management";
	}
	
	@GetMapping("/formAddPosition")
	public String addPositionForm(Position position, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		model.addAttribute("callFrom", callFrom);
		model.addAttribute("callFromID", callFromID);
		return "position-add";
	}
	
	@PostMapping("/addPosition")
	public String addPosition(@Valid Position position, BindingResult result, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		if (result.hasErrors()) {
			return "position-add";
		}
		if(positionService.findPositionByName(position.getName()) != null)
		{
			model.addAttribute("messages","Chức vụ đã tồn tại!");
			return "position-add";
		}
		else model.addAttribute("messages",null);
		
		positionService.addPosition(position);
		if(callFromID != null) return "redirect:" + callFrom + callFromID;
		return "redirect:" + callFrom;
	}
	
	@GetMapping("/formUpdatePosition")
	public String updatePositionForm(@RequestParam(name = "positionID")Long positionID, Model model) {
		Position position =  positionService.findPositionById(positionID);
		model.addAttribute("position", position);
		return "position-update";
	}

	@PostMapping("/updatePosition")
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
		
		positionService.updatePosition(position,positionID);
		return "redirect:/admin/positions";
	}
	
	@GetMapping("/deletePosition")
	public String deletePosition(@RequestParam(name = "positionID")Long positionID, Model model,HttpServletRequest request) {
		Position P = positionService.findPositionById(positionID);
		if(P.getEmployees().size() > 0) {
			model.addAttribute("messages","Không thể xóa chức vụ đang có nhân viên nắm giữ!");
			return showPositionList(1, "positionID", "asc", model,request);
		}
		positionService.deletePosition(positionID);
		return "redirect:/admin/positions";
		}
	 
}
