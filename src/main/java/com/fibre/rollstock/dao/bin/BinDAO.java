package com.fibre.rollstock.dao.bin;

import java.util.List;

import com.fibre.rollstock.model.Bin;

/**
 * This interface declares all known activities in handling bin records.
 * @author jeffrey.pogoy
 *
 */
public interface BinDAO {

	/**
	 * Creates a new bin record. 
	 * @param bin
	 * @return id of the newly created record
	 */
	public long create(Bin bin);
	
	/**
	 * Modifies an existing bin record.
	 * @param bin
	 */
	public void update(Bin bin);
	
	/**
	 * Removes a bin record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves bin records.
	 * @return list of bin records
	 */
	public List<Bin> findAll();
	
	/**
	 * Retrieves a bin record by id.
	 * @param binId
	 * @return bin record
	 */
	public Bin findById(long binId);
	
	/**
	 * Retrieves bin records by specific warehouse.
	 * @return list of bin records
	 */
	public List<Bin> findByWarehouse(long warehouseId);
	
	/**
	 * Retrieves available bin records by specific warehouse.
	 * @param warehouseId
	 * @return
	 */
	public List<Bin> findAvailableByWarehouse(long warehouseId);
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();	
}
