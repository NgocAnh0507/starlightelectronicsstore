package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.entities.helper.OrderDetailID;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.repository.OrderDetailRepository;

@Service
public class OderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public List<OrderDetail> getOrderDetails()
	{
		return orderDetailRepository.findAll();
	}
	
	public List<OrderDetail> getOrderDetailsByOrderID(long odID)
	{
		return orderDetailRepository.findOrderDetailByOrderID(odID);
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail)
	{
		OrderDetail orderDetailSaved = orderDetailRepository.save(orderDetail);
		return orderDetailSaved;
	}

	public OrderDetail addOrderDetail(CartLineInfo cartLineInfo, Long orderID)
	{
		long productID = cartLineInfo.getProductInfo().getProductID();
		
		OrderDetail orderDetail = new OrderDetail(new OrderDetailID(productID,orderID));
		orderDetail.setPrice(cartLineInfo.getProductInfo().getPrice());
		orderDetail.setQuantity(cartLineInfo.getQuantity());
		
		OrderDetail orderDetailSaved = orderDetailRepository.save(orderDetail);
		return orderDetailSaved;
	}
	
	public OrderDetail findOrderDetailById(Long orderDetailID)
	{
		return orderDetailRepository.findById(orderDetailID).get();
	}

	public OrderDetail updateOrderDetail(OrderDetail orderDetailNew, Long orderDetailID)
	{
		OrderDetail orderDetail = findOrderDetailById(orderDetailID);
		orderDetail.setOrderID(orderDetailNew.getOrderID());
		orderDetail.setProductID(orderDetailNew.getProductID());
		orderDetail.setPrice(orderDetailNew.getPrice());
		orderDetail.setQuantity(orderDetailNew.getQuantity());
		return orderDetailRepository.save(orderDetail);
	}

	public void deleteOrderDetail(Long orderDetailID)
	{
		orderDetailRepository.deleteById(orderDetailID);
	}
}
