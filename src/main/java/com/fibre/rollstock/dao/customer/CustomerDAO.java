package com.fibre.rollstock.dao.customer;

import java.util.List;

import com.fibre.rollstock.model.Customer;

/**
 * This interface declares all known activities in handling customer records.
 * @author jeffrey.pogoy
 *
 */
public interface CustomerDAO {

	/**
	 * Creates a new customer record. 
	 * @param customer
	 * @return id of the newly created record
	 */
	public long create(Customer customer);
	
	/**
	 * Modifies an existing customer record.
	 * @param customer
	 */
	public void update(Customer customer);
	
	/**
	 * Removes a customer record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves customer records. May provide filtered values using a keyword.
	 * @return list of customer records
	 */
	public List<Customer> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
