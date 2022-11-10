package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
import java.security.Principal;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.model.EmployeeInfo;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.PositionService;
import com.greenvn.starlightelectronicsstore.service.StorageService;

@Controller
@RequestMapping(value = "/admin")
public class EmployeeController {
	
	@Autowired
	private BCryptPasswordEncoder BcryptPasswordEncoder;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PositionService positionService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private ImageService imageService;
	
	@GetMapping("/employees")
	public String showEmployeeList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "employeeID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model ,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<Employee> pageEmployee = employeeService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Employee> employees = pageEmployee.getContent();
		if(employees.size() == 0) employees = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageEmployee.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","employees" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("employees",employees);
		return "employee-management";
	}
	
	@GetMapping("/formAddEmployee")
	public String addEmployeeForm(Employee employee,Model model,
		@RequestParam(name= "notice",required = false)String notice) {
		model.addAttribute("notice", notice);
		model.addAttribute("positions",positionService.getPositions());
		return "employee-add";
	}
	
	@PostMapping("/addEmployee")
	public String addEmployee(@Valid Employee employee, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		if (result.hasErrors()) {

			if(employee.getPhoneNumber().length() == 0 || employee.getPhoneNumber() == null) {
				model.addAttribute("messages", "Số điện thoại không được để trống!");
			}
			
	       if(employee.getPassword().isEmpty() || employee.getPassword() == null)
	       {
	           model.addAttribute("messagesPass", "Mật khẩu không được để trống!");
	       }
	       else if(employee.getPassword().length() < 8 || employee.getPassword().length() > 16) 
			{
				model.addAttribute("messagesPass", "Mật khẩu chỉ được chứa từ 8 đến 16 ký tự!");
				
			}
	       else model.addAttribute("messagesPass",null);
			
			model.addAttribute("notice", "Thêm nhân viên thất bại!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		
		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date currentDate= new java.util.Date(millis);  
		LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//

		// Ngày sinh nhân viên
		LocalDate birthdayLocalDate = employee.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears();
		
		if(age < 18 || age > 55) 
		{
			model.addAttribute("notice", "Thêm nhân viên thất bại!");
			model.addAttribute("birthdayMessages", "Tuổi nhân viên phải từ 18 đến 55!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		model.addAttribute("birthdayMessages",null);
		
		if(!employeeService.checkPhoneNumber(employee.getPhoneNumber()))
		{
			model.addAttribute("notice", "Thêm nhân viên thất bại!");
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		model.addAttribute("messages",null);
		
		if(employee.getPassword().length() < 8 || employee.getPassword().length() > 16) 
		{
			model.addAttribute("notice", "Thêm nhân viên thất bại!");
			model.addAttribute("messagesPass", "Mật khẩu chỉ được chứa từ 8 đến 16 ký tự!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-add";
		}
		model.addAttribute("messagesPass",null);

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File saveFile = storageService.storeImage(file, uploadRootPath);

		if(saveFile != null) {
			String name = file.getOriginalFilename();
			Image image = new Image();
			image.setImageURL(uploadRootPath);
			image.setName(name);
			image.setProduct(null);
			employee.setAvatar(imageService.addImage(image));
			model.addAttribute("noImage",null);
		}
		else{
			model.addAttribute("notice", "Thêm nhân viên thất bại!");
			model.addAttribute("noImage","Ảnh biểu tượng không được để trống!");
			return "employee-add";
		}
		
		employeeService.addEmployee(employee);
		return showEmployeeList(1,"employeeID","asc",model,request,"Thêm nhân viên thành công!");
	}
	
	@GetMapping("/formUpdateEmployee")
	public String updateEmployeeForm(@RequestParam(name = "employeeID")Long employeeID, Model model,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		Employee employee = employeeService.findEmployeeById(employeeID);
		model.addAttribute("employee", employee);
		model.addAttribute("positions",positionService.getPositions());
		return "employee-update";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(@RequestParam(name = "employeeID")Long employeeID,@Valid Employee employee, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		if(result.hasErrors()) {

			if(employee.getPhoneNumber().length() == 0 || employee.getPhoneNumber() == null) {
				model.addAttribute("messages", "Số điện thoại không được để trống!");
			}
			
	       if(employee.getPassword().isEmpty() || employee.getPassword() == null)
	       {
	           model.addAttribute("messagesPass", "Mật khẩu không được để trống!");
	       }
	       else if(employee.getPassword().length() < 8 || employee.getPassword().length() > 16) 
			{
				model.addAttribute("messagesPass", "Mật khẩu chỉ được chứa từ 8 đến 16 ký tự!");
				
			}
	       else model.addAttribute("messagesPass",null);
		       
	       Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
	       employee.setAvatar(currentImage);
	       model.addAttribute("employee", employee);
			model.addAttribute("notice", "Chỉnh sửa nhân viên thất bại!");
			model.addAttribute("positions",positionService.getPositions());
			return "employee-update";
		}
		
		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date currentDate= new java.util.Date(millis);  
		LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//

		// Ngày sinh nhân viên
		LocalDate birthdayLocalDate = employee.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears();
		
		if(age < 18 || age > 55) 
		{
			model.addAttribute("notice", "Chỉnh sửa nhân viên thất bại!");
			model.addAttribute("birthdayMessages", "Tuổi nhân viên phải từ 18 đến 55!");
			model.addAttribute("positions",positionService.getPositions());
			Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
	        employee.setAvatar(currentImage);
	        model.addAttribute("employee", employee);
			return "employee-update";
		}
		
		if(!employeeService.checkPhoneNumber(employee.getPhoneNumber()))
		{
			model.addAttribute("notice", "Chỉnh sửa nhân viên thất bại!");
			model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
	        employee.setAvatar(currentImage);
	        model.addAttribute("employee", employee);
			model.addAttribute("positions",positionService.getPositions());
			return "employee-update";
		}
        
       model.addAttribute("messages",null);
       

       if(employee.getPassword().isEmpty() || employee.getPassword() == null)
       {
    	   model.addAttribute("notice", "Chỉnh sửa nhân viên thất bại!");
           model.addAttribute("messagesPass", "Mật khẩu không được để trống!");
			Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
	        employee.setAvatar(currentImage);
	        model.addAttribute("employee", employee);
           return "employee-update";
       }
       else if(employee.getPassword().length() < 8 || employee.getPassword().length() > 16) 
		{
			model.addAttribute("notice", "Chỉnh sửa nhân viên thất bại!");
			model.addAttribute("messagesPass", "Mật khẩu chỉ được chứa từ 8 đến 16 ký tự!");
			Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
	        employee.setAvatar(currentImage);
	        model.addAttribute("employee", employee);
			return "employee-update";
		}
       else model.addAttribute("messagesPass",null);
		
		Image currentImage = employeeService.findEmployeeById(employeeID).getAvatar();
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File saveFile = storageService.storeImage(file, uploadRootPath);
		
		if(saveFile != null) {
			String name = file.getOriginalFilename();
			Image image = new Image();
			image.setImageURL(uploadRootPath);
			image.setName(name);
			image.setProduct(null);
			employee.setAvatar(imageService.addImage(image));
		}
		else employee.setAvatar(currentImage);
		employeeService.updateEmployee(employee, employeeID);
		if(saveFile != null && currentImage != null) {
			imageService.deleteImage(currentImage.getImageID());
		}
		
		return showEmployeeList(1,"employeeID","asc",model,request,"Chỉnh sửa nhân viên thành công!");
	}
    @GetMapping("/formUpdateInfoEmployee")
    public String updateEmployeeForm1(@RequestParam(name = "employeeID")Long employeeID, Model model) {
    	Employee employee = employeeService.findEmployeeById(employeeID);
        model.addAttribute("employee", employee);
        model.addAttribute("positions",positionService.getPositions());
		return "employee-update1";
	}
    @PostMapping("/formUpdateInfoEmployee")
    public String updateEmployee1(@RequestParam(name = "employeeID")Long employeeID,@Valid EmployeeInfo employee, BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("positions",positionService.getPositions());
            return "employee-update1";
        }
        if(!employeeService.checkPhoneNumber(employee.getPhoneNumber())) {
            model.addAttribute("messages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
            Employee emp = employeeService.findEmployeeById(employeeID);
            model.addAttribute("employee", emp);
            model.addAttribute("positions",positionService.getPositions());
        }
        else model.addAttribute("messages",null);
        employeeService.updateEmployee(employee, employeeID);
        return "redirect:/admin/infoUser";
    }
	
	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam(name = "employeeID")Long employeeID, Model model,HttpServletRequest request) {
		Employee E = employeeService.findEmployeeById(employeeID);
		if(E == null) return "redirect:/admin/employees";
		employeeService.deleteEmployee(employeeID);
		if(E.getAvatar() != null)imageService.deleteImage(E.getAvatar().getImageID());
		return showEmployeeList(1,"employeeID","asc",model,request,"Xóa nhân viên thành công!");
	}
	//Change PassWord
	@GetMapping("/change-PassWord")
	public String formChangePw() {
		return "changePw";
	}
	@PostMapping("/change-PassWord")
	public String changePassword(@RequestParam("oldPassword") String oldPassword
			,@RequestParam("newPassword") String newPassword,Principal principal,Model model
			) 
	{
		System.out.println("OLD PASSWORD"+oldPassword);
		System.out.println("NEW PASSWORD"+newPassword);
		String userName = principal.getName();
		Employee currentEmp = this.employeeService.findEmployeeByUserName(userName);
		System.out.println(currentEmp.getPassword());
		if(this.BcryptPasswordEncoder.matches(oldPassword, currentEmp.getPassword())) {
			
			currentEmp.setPassword((newPassword));
			this.employeeService.updateEmployee(currentEmp, currentEmp.getEmployeeID());
			
			
		}else {
			model.addAttribute("message", "Mật khẩu cũ không đúng");
			return "changePw";
		}
		return "redirect:/login";
		
	}
}

