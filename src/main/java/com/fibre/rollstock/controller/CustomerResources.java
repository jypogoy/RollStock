package com.fibre.rollstock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fibre.rollstock.model.Customer;
import com.fibre.rollstock.service.customer.CustomerService;

@RestController
@RequestMapping("/api_customers")
public class CustomerResources {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Customer> findAll() {
		return customerService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return customerService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Customer customer) {
		return customerService.create(customer);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Customer customer) {
		customerService.update(customer);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		customerService.delete(id);
	}
}
