package com.greenvn.starlightelectronicsstore.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
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

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.service.CustomerService;

@Controller
@RequestMapping(value = "/admin")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public String showCustomerList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "customerID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
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
	
	@GetMapping("/formAddCustomer")
	public String addCustomerForm(Customer customer) {
		return "customer-add";
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(@Valid Customer customer, BindingResult result,HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			if(customer.getPhoneNumber().length() == 0 || customer.getPhoneNumber() == null) {
				model.addAttribute("messages", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng!");
			}
			
			model.addAttribute("notice", "Th??m kh??ch h??ng th???t b???i!");
			return "customer-add";
		}
		
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("notice", "Th??m kh??ch h??ng th???t b???i!");
			model.addAttribute("messages", "S??? ??i???n tho???i ch??? g???m c??c ch??? s??? t??? 0 ?????n 9!");
			return "customer-add";
		}
		model.addAttribute("messages",null);
		
		if (customer.getBirthday() == null) {
			model.addAttribute("notice", "Th??m kh??ch h??ng th???t b???i!");
			model.addAttribute("birthdayMessages", "Ng??y sinh kh??ng ???????c ????? tr???ng!");
			return "customer-add";
		}
		else{
			// Ng??y hi???n t???i
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ng??y sinh kh??ch h??ng
			LocalDate birthdayLocalDate = customer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 10 || age > 95)
			{
				model.addAttribute("notice", "Th??m kh??ch h??ng th???t b???i!");
				model.addAttribute("birthdayMessages", "Tu???i kh??ch h??ng ph???i t??? 10 ?????n 95!");
				return "customer-add";
			}
		}
		model.addAttribute("birthdayMessages",null);
		
		customerService.addCustomer(customer);
		return showCustomerList(1,"customerID","asc",model,request,"Th??m kh??ch h??ng th??nh c??ng!");
	}
	
	@GetMapping("/formUpdateCustomer")
	public String updateCustomerForm(@RequestParam(name = "customerID")Long customerID, Model model) {
		Customer customer = customerService.findCustomerById(customerID);
		model.addAttribute("customer", customer);
		return "update-customer";
	}
	
	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@RequestParam(name = "customerID")Long customerID,@Valid Customer customer, BindingResult result,HttpServletRequest request, Model model){
		if(result.hasErrors()) {
			if(customer.getPhoneNumber().length() == 0 || customer.getPhoneNumber() == null) {
				model.addAttribute("messages", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng!");
			}
			
			model.addAttribute("notice", "Ch???nh s???a kh??ch h??ng th???t b???i!");
			return "update-customer";
		}
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("notice", "Ch???nh s???a kh??ch h??ng th???t b???i!");
			model.addAttribute("messages", "S??? ??i???n tho???i ch??? g???m c??c ch??? s??? t??? 0 ?????n 9!");
			Customer cus = customerService.findCustomerById(customerID);
			model.addAttribute("customer", cus);
			return "update-customer";
		}
        
        model.addAttribute("messages",null);
        

		if (customer.getBirthday() == null) {
			model.addAttribute("notice", "Ch???nh s???a kh??ch h??ng th???t b???i!");
			model.addAttribute("birthdayMessages", "Ng??y sinh kh??ng ???????c ????? tr???ng!");
			return "update-customer";
		}
		else{
			// Ng??y hi???n t???i
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ng??y sinh kh??ch h??ng
			LocalDate birthdayLocalDate = customer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 10 || age > 95)
			{
				model.addAttribute("notice", "Ch???nh s???a kh??ch h??ng th???t b???i!");
				model.addAttribute("birthdayMessages", "Tu???i kh??ch h??ng ph???i t??? 10 ?????n 95!");
				return "update-customer";
			}
		}
		model.addAttribute("birthdayMessages",null);
		
		customerService.updateCustomer(customer, customerID);
		return showCustomerList(1,"customerID","asc",model,request,"Ch???nh s???a kh??ch h??ng th??nh c??ng!");
	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(name = "customerID")Long customerID, Model model,HttpServletRequest request) {
		Customer customer =  customerService.findCustomerById(customerID);
		if(customer == null) return "redirect:/admin/customers";
		if(customer.getOrders().size() > 0 || customer.getOrders().size() > 0) {
			model.addAttribute("messages","Kh??ng th??? x??a kh??ch h??ng ??ang c?? h??a ????n!");
			return showCustomerList(1,"customerID","asc",model,request,"X??a kh??ch h??ng th???t b???i!");
		}
		customerService.deleteCustomer(customerID);
		return showCustomerList(1,"customerID","asc",model,request,"X??a kh??ch h??ng th??nh c??ng!");
	}
}

