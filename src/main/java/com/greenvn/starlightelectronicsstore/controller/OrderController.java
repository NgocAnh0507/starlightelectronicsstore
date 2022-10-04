package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.service.OrderService;

@Controller
@RequestMapping(value = "/admin")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public String showOrderList(Model model)
	{
		model.addAttribute("orders",orderService.getOrders());
		return "orders";
				
	}
	
	@GetMapping("/formAddOrder")
	public String addOrderForm(Order order) {
		return "add-order";
	}
	
	@PostMapping("/addOrder")
	public String addOrder(@Valid Order order, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-order";
		}
		orderService.addOrder(order);
		return "";
	}

	@GetMapping("/formUpdateOrder")
	public String updateOrderForm(@RequestParam(name = "orderID")Long orderID, Model model) {
		Order order =  orderService.findOrderById(orderID);
		model.addAttribute("order", order);
		return "update-order";
	}

	@PostMapping("/updateOrder")
	public String updateOrder(@RequestParam(name = "orderID")Long orderID,@Valid Order order, BindingResult result, Model model){
		if(result.hasErrors()) {
			order.setOrderID(orderID);
			return "update-order";
		}
		orderService.updateOrder(order,orderID);
		return "";
	}

	@GetMapping("/deleteOrder")
	public String deleteOrder(@RequestParam(name = "orderID")Long orderID, Model model) {
		orderService.deleteOrder(orderID);
		return "";
	}
}
