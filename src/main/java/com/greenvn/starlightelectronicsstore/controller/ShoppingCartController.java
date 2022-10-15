package com.greenvn.starlightelectronicsstore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.model.CustomerInfo;
import com.greenvn.starlightelectronicsstore.model.ProductInfo;
import com.greenvn.starlightelectronicsstore.repository.ProductRepository;
import com.greenvn.starlightelectronicsstore.service.CustomerService;
import com.greenvn.starlightelectronicsstore.service.OderDetailService;
import com.greenvn.starlightelectronicsstore.service.OrderService;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.utils.Utils;

@Controller
@RequestMapping(value = "/shop")
public class ShoppingCartController {
	@Autowired
	private ProductService proSer;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OderDetailService oderDetailService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/buyProduct" }, method = RequestMethod.GET)
	public String listProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "productID", defaultValue = "") Long productID) {
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request); // Lấy giỏ hàng hiện tại ra
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1.0);
		}
		return "redirect:/shop/shoppingCart";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, @ModelAttribute("cartForm") CartInfo cartForm)

	{
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		return "redirect:/shop/shoppingCart";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {
		CartInfo myCart = Utils.getCartInSession(request);
		model.addAttribute("cartForm", myCart);
		return "shop/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model,
			//
			@RequestParam(value = "productID", defaultValue = "") Long productID) {
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request);
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
		}
		return "redirect:/shop/shoppingCart";
	}
	
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
		public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
		CartInfo cartInfo = Utils.getCartInSession(request);
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}
		cartInfo.setFastShip(false);
		model.addAttribute("customerInfo", customerInfo);
		model.addAttribute("cartInfo", cartInfo);
		return "shop/checkOut";
	}
	
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
			public String shoppingCartCustomerSave(@ModelAttribute("customerInfo") CustomerInfo customerInfo,
			HttpServletRequest request, Model model) throws ParseException 
	{
		CartInfo cartInfo = Utils.getCartInSession(request);
	    
		Boolean checkCustomer = true;
		if (customerInfo.getName() == null || customerInfo.getName().isEmpty()) {
			model.addAttribute("nameMessages", "Họ và tên không được để trống!");
			checkCustomer = false;
		}
		if (customerInfo.getBirthday() == null) {
			model.addAttribute("birthdayMessages", "Ngày sinh không được để trống!");
			checkCustomer = false;
		}
		else{
			// Ngày hiện tại
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ngày sinh khách hàng
			LocalDate birthdayLocalDate = customerInfo.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 16 || age > 95)
			{
				model.addAttribute("birthdayMessages", "Tuổi khách hàng phải từ 16 đến 95!");
				checkCustomer = false;
			}
		}
		
		if (customerInfo.getStreet() == null ||
				customerInfo.getStreet().isEmpty()) {
			model.addAttribute("streetMessages", "Số nhà, tên đường, phường không được để trống!");
			checkCustomer = false;
		}
		if (customerInfo.getDistrict() == null ||
				customerInfo.getDistrict().isEmpty()) {
			model.addAttribute("districtMessages", "Quận, huyện, thành phố không được để trống!");
			checkCustomer = false;
		}
		if (customerInfo.getCity() == null ||
				customerInfo.getCity().isEmpty()) {
			model.addAttribute("cityMessages", "Tỉnh (thành phố trực thuộc trung ương) không được để trống!");
			checkCustomer = false;
		}
		if (customerInfo.getEmail() == null ||
				customerInfo.getEmail().isEmpty()) {
			model.addAttribute("emailMessages", "Email không được để trống!");
			checkCustomer = false;
		}
		if (customerInfo.getNumberPhone() == null ||
				customerInfo.getNumberPhone().isEmpty()) {
			model.addAttribute("phoneMessages", "Số điện thoại không được để trống!");
			checkCustomer = false;
		}
		else if(!customerService.checkPhoneNumber(customerInfo.getNumberPhone())) {
			model.addAttribute("phoneMessages", "Số điện thoại chỉ gồm các chữ số từ 0 đến 9!");
			checkCustomer = false;
		}
		
		if (checkCustomer == false) {
			model.addAttribute("customerInfo", customerInfo);
			model.addAttribute("cartInfo", cartInfo);
			return "shop/checkOut";
		} 

		Customer customerSaved = customerService.addCustomer(customerInfo);
		cartInfo.setCustomerInfo(customerInfo);
		
		Order orderSaved = orderService.addOrder(cartInfo, customerSaved);
		
		for (CartLineInfo line : cartInfo.getCartLines()) {
			oderDetailService.addOrderDetail(line, orderSaved.getOrderID());
		}
		return "redirect:/shop/orderInfo?orderID="+orderSaved.getOrderID();
	}
	
	@RequestMapping(value = { "/orderInfo" }, method = RequestMethod.GET)
		public String orderInfo(HttpServletRequest request, Model model,
				@RequestParam(value = "orderID") Long orderID) 
	{
		Order order = orderService.findOrderById(orderID);
		Customer customer = order.getCustomer();
		
		List<OrderDetail> detailList = oderDetailService.getOrderDetailsByOrderID(orderID);
		List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
		Double total = 0.0;
		for(OrderDetail detail : detailList ) {
			long productID = detail.getProductID();
			ProductInfo productInfo = new ProductInfo(productService.findProductById(productID));
			CartLineInfo newCartLine = new CartLineInfo();
			
			newCartLine.setProductInfo(productInfo);
			newCartLine.setQuantity(detail.getQuantity());
			total += detail.getQuantity() * detail.getPrice();
			
			cartLines.add(newCartLine);
		}

		model.addAttribute("order", order);
		model.addAttribute("customer", customer);
		model.addAttribute("total", total);
		model.addAttribute("cartLines", cartLines);
		model.addAttribute("fastShip", order.getFastShip());
		return "shop/orderInfo";
	}
	
}
