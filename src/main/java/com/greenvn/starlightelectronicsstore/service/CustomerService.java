package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getCustomers()
	{
		return customerRepository.findAll();
	}
	
	public Customer addCustomer(Customer customer)
	{
		Customer customerSaved = customerRepository.save(customer);
		return customerSaved;
	}
	
	public Customer findCustomerById(Long customerID)
	{
		return customerRepository.findById(customerID).get();
	}
	
	public Customer updateCustomer(Customer customertNew, Long customerID)
	{
		Customer customer = findCustomerById(customerID);
		customer.setAddress(customertNew.getAddress());
		customer.setBithYear(customertNew.getBithYear());
		customer.setEmail(customertNew.getEmail());
		customer.setName(customertNew.getName());
		customer.setPhoneNumber(customertNew.getPhoneNumber());
		return customerRepository.save(customer);
	}
	
	public void deleteCustomer(Long customerID)
	{
		customerRepository.deleteById(customerID);
	}
}
	
