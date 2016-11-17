package com.fibre.rollstock.service.van;

import java.util.List;

import com.fibre.rollstock.model.VanImage;

public interface VanImageService {

	/**
	 * Creates a new van image record. 
	 * @param image
	 * @return id of the newly created record
	 */
	public long create(VanImage image);
	
	/**
	 * Modifies an existing van's image record.
	 * @param image
	 */
	public void update(VanImage image);
	
	/**
	 * Removes a van's image record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves van's image records.
	 * @return list of van's image records
	 */
	public List<VanImage> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
