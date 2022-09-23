package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Order;
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
		return orderRepository.save(order);
	}
	
	public void deleteOrder(Long orderID)
	{
		orderRepository.existsById(orderID);
	}
}
