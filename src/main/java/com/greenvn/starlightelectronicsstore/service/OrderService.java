package com.greenvn.starlightelectronicsstore.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getOrders()
	{
		return orderRepository.findAll();
	}
	
	public Order addOrder(Order order)
	{
		Order orderSaved = orderRepository.save(order);
		return orderSaved;
	}

	public Order addOrder(CartInfo cartInfo, Customer customer)
	{
		Order order = new Order();
		
		Double amount = 0.0, total = 0.0;
		for (CartLineInfo line : cartInfo.getCartLines()) {
			amount += line.getQuantity();
			total += line.getProductInfo().getPrice() * line.getQuantity();;
		}
		
		long millis=System.currentTimeMillis(); 
		Date date= new java.util.Date(millis);
		
		order.setCustomer(customer);
		order.setOrderStatus("Đã nhận đơn");
		order.setAmount(amount);
		order.setTotal(total);
		order.setOrderDate(date);
		order.setFastShip(cartInfo.getFastShip());
		
		Order orderSaved = orderRepository.save(order);
		return orderSaved;
	}
	
	public Order findOrderById(Long orderID)
	{
		return orderRepository.findById(orderID).get();
	}
	
	public Order updateOrder(Order orderNew, Long orderID)
	{
		Order order = findOrderById(orderID);
		order.setCustomer(orderNew.getCustomer());
		order.setOrderStatus(orderNew.getOrderStatus());
		order.setAmount(orderNew.getAmount());
		order.setTotal(orderNew.getTotal());
		order.setOrderDate(orderNew.getOrderDate());
		order.setFastShip(orderNew.getFastShip());
		return orderRepository.save(order);
	}
	
	public void deleteOrder(Long orderID)
	{
		orderRepository.deleteById(orderID);
	}
}
