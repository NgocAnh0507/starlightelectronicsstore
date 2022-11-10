package com.greenvn.starlightelectronicsstore.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.OderStatus;
import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.model.ProductInfo;
import com.greenvn.starlightelectronicsstore.service.OderDetailService;
import com.greenvn.starlightelectronicsstore.service.OrderService;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
@RequestMapping(value = "/admin")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OderDetailService oderDetailService;

	@Autowired
	private ProductService productService;
	
	@GetMapping("/orders")
	public String showOrderList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "orderID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<Order> pageOrder = orderService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Order> orders = pageOrder.getContent();
		if(orders.size() == 0) orders = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageOrder.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","orders" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("orders",orders);
		return "order-management";
				
	}

	@GetMapping("/formUpdateOrder")
	public String updateOrderForm(@RequestParam(name = "orderID")Long orderID, Model model) {
		Order order =  orderService.findOrderById(orderID);
		CartInfo cartInfo = new CartInfo();
		cartInfo.setStatus(order.getOrderStatus().toString());

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("orderID", orderID);
		return "order-update";
	}

	/*
	 * @PostMapping("/updateOrder") public String updateOrder(@RequestParam(name =
	 * "orderID")Long orderID, CartInfo cartInfo, BindingResult result, Model
	 * model){
	 * 
	 * Order order = orderService.findOrderById(orderID);
	 * 
	 * if(cartInfo.getStatus() == null || cartInfo.getStatus().isEmpty()) {
	 * model.addAttribute("messages", "Trạng thái đơn hàng không được để trống!");
	 * 
	 * model.addAttribute("cartInfo", cartInfo); model.addAttribute("order", order);
	 * return "order-update"; } else if(cartInfo.getStatus().equals("0"))
	 * order.setOrderStatus(OderStatus.RECEIVED); else
	 * if(cartInfo.getStatus().equals("1"))
	 * order.setOrderStatus(OderStatus.PACKAGED); else
	 * if(cartInfo.getStatus().equals("2"))
	 * order.setOrderStatus(OderStatus.DELIVERED); else
	 * if(cartInfo.getStatus().equals("3"))
	 * order.setOrderStatus(OderStatus.CANCELED);
	 * 
	 * orderService.updateOrder(order,orderID); return "redirect:/admin/orders"; }
	 */

	@GetMapping("/updateOrderStatus")
	public String updateOrderStatus(@RequestParam(name = "orderID")Long orderID, String status, Model model){

		Order order =  orderService.findOrderById(orderID);
		
		if(status.equals("0")) order.setOrderStatus(OderStatus.RECEIVED);
		else if(status.equals("1")) order.setOrderStatus(OderStatus.PACKAGED);
		else if(status.equals("2")) order.setOrderStatus(OderStatus.DELIVERING);
		else if(status.equals("3")) order.setOrderStatus(OderStatus.DELIVERED);
		else if(status.equals("4")) order.setOrderStatus(OderStatus.ORDER_RETURNED);
		else if(status.equals("5")) order.setOrderStatus(OderStatus.CANCELED);
		
		orderService.updateOrder(order,orderID);
		return "redirect:/admin/orderDetail?orderID=" + orderID;
	}
	
	@GetMapping("/deleteOrder")
	public String deleteOrder(@RequestParam(name = "orderID")Long orderID, Model model,HttpServletRequest request) {
		Order O = orderService.findOrderById(orderID);
		if(O == null) return "redirect:/admin/orders";
		orderService.deleteOrder(orderID);
		return showOrderList(1,"orderID","asc",model,request,"Xóa đơn hàng thành công!");
	}
	

	@GetMapping("/orderDetail")
		public String orderDetail(HttpServletRequest request, Model model,
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
	//	model.addAttribute("fastShip", order.getFeeShip());
		return "order-detail";
	}
}
