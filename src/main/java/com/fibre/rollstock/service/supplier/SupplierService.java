package com.fibre.rollstock.service.supplier;

import java.util.List;

import com.fibre.rollstock.model.Supplier;

public interface SupplierService {

	/**
	 * Creates a new supplier record.
	 * @param supplier
	 * @return id of the newly created record
	 */
	public long create(Supplier supplier);
	
	/**
	 * Modifies an existing supplier record.
	 * @param supplier
	 */
	public void update(Supplier supplier);
	
	/**
	 * Removes a supplier record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves supplier records. May provide filtered values using a keyword.
	 * @return list of supplier records
	 */
	public List<Supplier> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
