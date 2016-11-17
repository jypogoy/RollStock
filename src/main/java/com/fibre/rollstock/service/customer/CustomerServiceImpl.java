package com.fibre.rollstock.service.customer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.customer.CustomerDAO;
import com.fibre.rollstock.model.Customer;

@Service
@Configurable
public class CustomerServiceImpl implements CustomerService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerDAO customerDao;

	@Override
	public long create(Customer customer) {
		
		long generatedKey = customerDao.create(customer);
		
		LOGGER.info("Customer record '" + customer.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Customer customer) {
		
		customerDao.update(customer);
		
		LOGGER.info("Customer record '" + customer.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		customerDao.delete(id);
		
		LOGGER.info("Customer record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}
	
	@Override
	public int count() {
		return customerDao.count();
	}
}
