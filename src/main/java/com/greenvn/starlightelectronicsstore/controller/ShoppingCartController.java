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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.model.CustomerInfo;
import com.greenvn.starlightelectronicsstore.model.ManufacturerInfo;
import com.greenvn.starlightelectronicsstore.model.ProductInfo;
import com.greenvn.starlightelectronicsstore.repository.ProductRepository;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.CustomerService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
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
	
    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private CategoryService categoryService;
	

	@RequestMapping(value = { "/buyProduct" }, method = RequestMethod.GET)
	public String listProductHandler(HttpServletRequest request, Model model,
			@RequestParam(value = "productID", defaultValue = "") Long productID) {

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request); // L???y gi??? h??ng hi???n t???i ra
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1, product.getQuantity());
		}
		return "redirect:/shop/shoppingCart";
	}
	@RequestMapping(value = { "/addProductToCart" }, method = RequestMethod.GET)
	public String addProductToCart(HttpServletRequest request, Model model,
			@RequestParam(value = "productID", defaultValue = "") Long productID) {

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		Product product = null;
		if (productID != null) {
			product = proSer.findProductById(productID);
		}
		if (product != null) {
			CartInfo cartInfo = Utils.getCartInSession(request); // L???y gi??? h??ng hi???n t???i ra
			ProductInfo productInfo = new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1, product.getQuantity());
		}
		return "redirect:/";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, //
			Model model, @ModelAttribute("cartForm") CartInfo cartForm)

	{

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		CartInfo cartInfo = Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		return "redirect:/shop/shoppingCart";
	}

	@RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request, Model model) {

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		CartInfo myCart = Utils.getCartInSession(request);
		model.addAttribute("cartForm", myCart);
		return "shop/shoppingCart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model,
			//
			@RequestParam(value = "productID", defaultValue = "") Long productID) {

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
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

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		CartInfo cartInfo = Utils.getCartInSession(request);
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}
	//	cartInfo.getFeeShip();
		model.addAttribute("customerInfo", customerInfo);
		model.addAttribute("cartInfo", cartInfo);
		return "shop/checkOut";
	}
	
	@RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
			public String shoppingCartCustomerSave(@ModelAttribute("customerInfo") CustomerInfo customerInfo,
			HttpServletRequest request, Model model) throws ParseException 
	{

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		CartInfo cartInfo = Utils.getCartInSession(request);
	    
		Boolean checkCustomer = true;
		if (customerInfo.getName() == null || customerInfo.getName().isEmpty()) {
			model.addAttribute("nameMessages", "H??? v?? t??n kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		if (customerInfo.getBirthday() == null) {
			model.addAttribute("birthdayMessages", "Ng??y sinh kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		else{
			// Ng??y hi???n t???i
			long millis = System.currentTimeMillis(); 
			Date currentDate = new java.util.Date(millis);  
			LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//

			// Ng??y sinh kh??ch h??ng
			LocalDate birthdayLocalDate = customerInfo.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long age =  Period.between(birthdayLocalDate, currentLocalDate).getYears(); 
			
			if(age < 10 || age > 95)
			{
				model.addAttribute("birthdayMessages", "Tu???i kh??ch h??ng ph???i t??? 10 ?????n 95!");
				checkCustomer = false;
			}
		}
		
		if (customerInfo.getStreet() == null ||
				customerInfo.getStreet().isEmpty()) {
			model.addAttribute("streetMessages", "S??? nh??, t??n ???????ng, ph?????ng kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		if (customerInfo.getDistrict() == null ||
				customerInfo.getDistrict().isEmpty()) {
			model.addAttribute("districtMessages", "Qu???n, huy???n, th??nh ph??? kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		if (customerInfo.getCity() == null ||
				customerInfo.getCity().isEmpty()) {
			model.addAttribute("cityMessages", "T???nh (th??nh ph??? tr???c thu???c trung ????ng) kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		if (customerInfo.getEmail() == null ||
				customerInfo.getEmail().isEmpty()) {
			model.addAttribute("emailMessages", "Email kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		if (customerInfo.getNumberPhone() == null ||
				customerInfo.getNumberPhone().isEmpty()) {
			model.addAttribute("phoneMessages", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng!");
			checkCustomer = false;
		}
		else if(!customerService.checkPhoneNumber(customerInfo.getNumberPhone())) {
			model.addAttribute("phoneMessages", "S??? ??i???n tho???i ch??? g???m c??c ch??? s??? t??? 0 ?????n 9!");
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
				@RequestParam(value = "orderID",name = "orderID",defaultValue = "0") Long orderID) 
	{

        // Ph???i c?? cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		Order order = orderService.findOrderById(orderID);
		if(order == null) {
			
			model.addAttribute("messages", "M?? h??a ????n kh??ng t???n t???i!");
			return "shop/orderInfo";
		}
		else model.addAttribute("messages",null);
		
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
		//model.addAttribute("fastShip", order.getFeeShip());
		return "shop/orderInfo";
	}
	
}
