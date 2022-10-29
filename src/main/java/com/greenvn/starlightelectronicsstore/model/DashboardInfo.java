package com.greenvn.starlightelectronicsstore.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.entities.Order;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.service.EmployeeService;
import com.greenvn.starlightelectronicsstore.service.OrderService;
import com.greenvn.starlightelectronicsstore.service.ProductService;

public class DashboardInfo {


	private Integer orderCount;
	private Integer receivedOrderCount;
	private Integer packagedOrderCount;
	private Integer deliveringOrderCount;
	private Integer deliveredOrderCount;
	private Integer returnedOrderCount;
	private Integer canceledOrderCount;
	private Integer employeeCount;
	private Integer productCount;
	private Double incomingMoney;
	
	public void Sumary(List<Order> orders, List<Employee> employees, List<Product> products) {
		
		// Orders and Money
		this.orderCount = 0;
		this.receivedOrderCount = 0;
		this.packagedOrderCount = 0;
		this.deliveringOrderCount = 0;
		this.deliveredOrderCount = 0;
		this.returnedOrderCount = 0;
		this.canceledOrderCount = 0;
		this.incomingMoney = 0.0;
		
		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date currentDate= new java.util.Date(millis);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		
		int currentMonth = calendar.get(Calendar.MONTH);
		
		for(Order o : orders) {
			// Bỏ qua nếu không phải tháng hiện tại
			calendar.setTime(o.getOrderDate());
			int month = calendar.get(Calendar.MONTH);
			if(month != currentMonth) continue;
			
			this.orderCount++;
			switch ( o.getOrderStatus() ) {
			    case  RECEIVED:
			    	this.receivedOrderCount++;
			        break;
			    case  PACKAGED:
			    	this.packagedOrderCount++;
			        break;
			    case  DELIVERING:
			    	this.deliveringOrderCount++;
			        break;
			    case  DELIVERED:
			    	this.deliveredOrderCount++;
					this.incomingMoney += o.getTotal();
			        break;
			    case  ORDER_RETURNED:
			    	this.returnedOrderCount++;
			        break;
			    case  CANCELED:
			    	this.canceledOrderCount++;
			        break;
			}
		}
		
		//Employees
		this.employeeCount = 0;
		for(Employee e : employees) {
			if(e.getIsActive()) this.employeeCount++;
		}
		
		//Products
		this.productCount = 0;
		for(Product p : products) {
			this.productCount+=p.getQuantity();
		}
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		orderCount = orderCount;
	}

	public Integer getReceivedOrderCount() {
		return receivedOrderCount;
	}

	public void setReceivedOrderCount(Integer receivedOrderCount) {
		this.receivedOrderCount = receivedOrderCount;
	}

	public Integer getPackagedOrderCount() {
		return packagedOrderCount;
	}

	public void setPackagedOrderCount(Integer packagedOrderCount) {
		this.packagedOrderCount = packagedOrderCount;
	}

	public Integer getDeliveringOrderCount() {
		return deliveringOrderCount;
	}

	public void setDeliveringOrderCount(Integer deliveringOrderCount) {
		this.deliveringOrderCount = deliveringOrderCount;
	}

	public Integer getDeliveredOrderCount() {
		return deliveredOrderCount;
	}

	public void setDeliveredOrderCount(Integer deliveredOrderCount) {
		this.deliveredOrderCount = deliveredOrderCount;
	}

	public Integer getReturnedOrderCount() {
		return returnedOrderCount;
	}

	public void setReturnedOrderCount(Integer returnedOrderCount) {
		this.returnedOrderCount = returnedOrderCount;
	}

	public Integer getCanceledOrderCount() {
		return canceledOrderCount;
	}

	public void setCanceledOrderCount(Integer canceledOrderCount) {
		this.canceledOrderCount = canceledOrderCount;
	}

	public Integer getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(Integer employeeCount) {
		this.employeeCount = employeeCount;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Double getIncomingMoney() {
		return incomingMoney;
	}

	public void setIncomingMoney(Double incomingMoney) {
		this.incomingMoney = incomingMoney;
	}

	public DashboardInfo(Integer orderCount, Integer receivedOrderCount, Integer packagedOrderCount,
			Integer deliveringOrderCount, Integer deliveredOrderCount, Integer returnedOrderCount,
			Integer canceledOrderCount, Integer employeeCount, Integer productCount, Double incomingMoney) {
		super();
		this.orderCount = orderCount;
		this.receivedOrderCount = receivedOrderCount;
		this.packagedOrderCount = packagedOrderCount;
		this.deliveringOrderCount = deliveringOrderCount;
		this.deliveredOrderCount = deliveredOrderCount;
		this.returnedOrderCount = returnedOrderCount;
		this.canceledOrderCount = canceledOrderCount;
		this.employeeCount = employeeCount;
		this.productCount = productCount;
		this.incomingMoney = incomingMoney;
	}

	public DashboardInfo() {
		super();
	}
	
	
	
}
