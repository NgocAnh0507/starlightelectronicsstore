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
				model.addAttribute("messages", "Số điện thoại không được để trống!");
			}
			
			model.addAttribute("notice", "Thêm khách hàng thất bại!");
			return "customer-add";
		}
		
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("notice", "Thêm khách hàng thất bại!");
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			return "customer-add";
		}
		model.addAttribute("messages",null);
		
		if (customer.getBirthday() == null) {
			model.addAttribute("notice", "Thêm khách hàng thất bại!");
			model.addAttribute("birthdayMessages", "Ngày sinh không được để trống!");
			return "customer-add";
		}
		else{
			// Ngày hiện tại
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ngày sinh khách hàng
			LocalDate birthdayLocalDate = customer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 10 || age > 95)
			{
				model.addAttribute("notice", "Thêm khách hàng thất bại!");
				model.addAttribute("birthdayMessages", "Tuổi khách hàng phải từ 10 đến 95!");
				return "customer-add";
			}
		}
		model.addAttribute("birthdayMessages",null);
		
		customerService.addCustomer(customer);
		return showCustomerList(1,"customerID","asc",model,request,"Thêm khách hàng thành công!");
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
				model.addAttribute("messages", "Số điện thoại không được để trống!");
			}
			
			model.addAttribute("notice", "Chỉnh sửa khách hàng thất bại!");
			return "update-customer";
		}
		if(!customerService.checkPhoneNumber(customer.getPhoneNumber()))
		{
			model.addAttribute("notice", "Chỉnh sửa khách hàng thất bại!");
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			Customer cus = customerService.findCustomerById(customerID);
			model.addAttribute("customer", cus);
			return "update-customer";
		}
        
        model.addAttribute("messages",null);
        

		if (customer.getBirthday() == null) {
			model.addAttribute("notice", "Chỉnh sửa khách hàng thất bại!");
			model.addAttribute("birthdayMessages", "Ngày sinh không được để trống!");
			return "update-customer";
		}
		else{
			// Ngày hiện tại
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ngày sinh khách hàng
			LocalDate birthdayLocalDate = customer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 10 || age > 95)
			{
				model.addAttribute("notice", "Chỉnh sửa khách hàng thất bại!");
				model.addAttribute("birthdayMessages", "Tuổi khách hàng phải từ 10 đến 95!");
				return "update-customer";
			}
		}
		model.addAttribute("birthdayMessages",null);
		
		customerService.updateCustomer(customer, customerID);
		return showCustomerList(1,"customerID","asc",model,request,"Chỉnh sửa khách hàng thành công!");
	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(name = "customerID")Long customerID, Model model,HttpServletRequest request) {
		Customer customer =  customerService.findCustomerById(customerID);
		if(customer == null) return "redirect:/admin/customers";
		if(customer.getOrders().size() > 0 || customer.getOrders().size() > 0) {
			model.addAttribute("messages","Không thể xóa khách hàng đang có hóa đơn!");
			return showCustomerList(1,"customerID","asc",model,request,"Xóa khách hàng thất bại!");
		}
		customerService.deleteCustomer(customerID);
		return showCustomerList(1,"customerID","asc",model,request,"Xóa khách hàng thành công!");
	}
}

