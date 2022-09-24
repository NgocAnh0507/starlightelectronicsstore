package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public String showCustomerList(Model model)
	{
		model.addAttribute("customers",customerService.getCustomers());
		return "/customers";
	}
	
	@GetMapping("/formAddCustomer")
	public String addCustomerForm(Customer customer) {
		return "add-customer";
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-customer";
		}
		customerService.addCustomer(customer);
		return "";
	}
	
	@GetMapping("/formUpdateCustomer")
	public String updateCustomerForm(@RequestParam(name = "customerID")Long customerID, Model model) {
		Customer customer = customerService.findCustomerById(customerID);
		model.addAttribute("customer", customer);
		return "update-customer";
	}
	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestParam(name = "customerID")Long customerID,@Valid Customer customer, BindingResult result, Model model){
		if(result.hasErrors()) {
			customer.setCustomerID(customerID);
			return "update-customer";
		}
		customerService.updateCustomer(customer, customerID);
		return "";
	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(name = "customerID")Long customerID, Model model) {
		customerService.deleteCustomer(customerID);
		return "";
	}
}
