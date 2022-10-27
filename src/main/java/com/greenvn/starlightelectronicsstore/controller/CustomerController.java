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
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/admin/customers")
	public String showCustomerList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "customerID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model,HttpServletRequest request)
	{
		int pageSize = 9;
		Page<Customer> pageCustomer = customerService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Customer> customers = pageCustomer.getContent();
		if(customers.size() == 0) customers = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageCustomer.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","customers" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("customers",customers);
		return "customer-management";
	}
	
	@GetMapping("/admin/formAddCustomer")
	public String addCustomerForm(Customer customer) {
		return "customer-add";
	}
	
	@PostMapping("/admin/addCustomer")
	public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "customer-add";
		}
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			return "customer-add";
		}
		model.addAttribute("messages",null);
		customerService.addCustomer(customer);
		return "redirect:/admin/customers";
	}
	
	@GetMapping("/admin/formUpdateCustomer")
	public String updateCustomerForm(@RequestParam(name = "customerID")Long customerID, Model model) {
		Customer customer = customerService.findCustomerById(customerID);
		model.addAttribute("customer", customer);
		return "update-customer";
	}
	
	
	@PostMapping("/admin/updateCustomer")
	public String updateCustomer(@RequestParam(name = "customerID")Long customerID,@Valid Customer customer, BindingResult result, Model model){
		if(result.hasErrors()) {
			customer.setCustomerID(customerID);
			return "update-customer";
		}
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			Customer cus = customerService.findCustomerById(customerID);
			model.addAttribute("customer", cus);
			return "update-customer";
		}
        
        model.addAttribute("messages",null);
		customerService.updateCustomer(customer, customerID);
		return "redirect:/admin/customers";
	}
	
	@GetMapping("/admin/deleteCustomer")
	public String deleteCustomer(@RequestParam(name = "customerID")Long customerID, Model model) {
		customerService.deleteCustomer(customerID);
		return "redirect:/admin/customers";
	}
}

