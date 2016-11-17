package com.fibre.rollstock.dao.warehouse;

import java.util.List;

import com.fibre.rollstock.model.Warehouse;

/**
 * This interface declares all known activities in handling warehouse records.
 * @author jeffrey.pogoy
 *
 */
public interface WarehouseDAO {

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
	 * Retrieves warehouse records.
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
