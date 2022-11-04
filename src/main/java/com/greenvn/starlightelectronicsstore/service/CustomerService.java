package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Customer;
import com.greenvn.starlightelectronicsstore.entities.Employee;
import com.greenvn.starlightelectronicsstore.model.CustomerInfo;
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

	public boolean checkPhoneNumber(String numberPhone)
	{ 
		for(int i=0; i<numberPhone.length(); i++)
		{
			if(numberPhone.charAt(i) < '0' || numberPhone.charAt(i) > '9')
				return false;
		}
		return true;
	}

	public Customer addCustomer(CustomerInfo customerInfo)
	{
		String address = customerInfo.getStreet() + ", " + customerInfo.getDistrict() + ", " + customerInfo.getCity();
				
		Customer customer = new Customer();
		customer.setAddress(address);
		customer.setBirthday(customerInfo.getBirthday());
		customer.setEmail(customerInfo.getEmail());
		customer.setName(customerInfo.getName());
		customer.setPhoneNumber(customerInfo.getNumberPhone());
		
		Customer customerSaved = customerRepository.save(customer);
		return customerSaved;
	}
	
	public Customer findCustomerById(Long customerID)
	{
		if(customerRepository.findById(customerID).isEmpty()) return null;
		return customerRepository.findById(customerID).get();
	}
	
	public Customer updateCustomer(Customer customertNew, Long customerID)
	{
		Customer customer = findCustomerById(customerID);
		if(customer == null) return null;
		customer.setAddress(customertNew.getAddress());
		customer.setBirthday(customertNew.getBirthday());
		customer.setEmail(customertNew.getEmail());
		customer.setName(customertNew.getName());
		customer.setPhoneNumber(customertNew.getPhoneNumber());
		return customerRepository.save(customer);
	}
	
	public void deleteCustomer(Long customerID)
	{
		customerRepository.deleteById(customerID);
	}
	
	// Pageable
		public Page<Customer> findAll(int pageNo, int pageSize, String sortField, String sortDirection) {

			// sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
					: Sort.by(sortField).descending();

			Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
			Page<Customer> pageCustomer = customerRepository.findAll(pageable);
			return pageCustomer;
		}
	
}
	
