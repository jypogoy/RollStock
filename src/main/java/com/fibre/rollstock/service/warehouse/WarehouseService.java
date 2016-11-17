package com.fibre.rollstock.service.warehouse;

import java.util.List;

import com.fibre.rollstock.model.Warehouse;

public interface WarehouseService {

	/**
	 * Creates a new warehouse record. 
	 * @param warehouse
	 * @return id of the newly created record
	 */
	public long create(Warehouse warehouse);
	
	/**
	 * Modifies an existing warehouse record.
	 * @param warehouse
	 */
	public void update(Warehouse warehouse);
	
	/**
	 * Removes a warehouse record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves warehouse records. May provide filtered values using a keyword.
	 * @return list of warehouse records
	 */
	public List<Warehouse> findAll();
	
	/**
	 * Retrieves warehouse record by id.
	 * @param id
	 * @return warehouse record
	 */
	public Warehouse findById(long id);
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
