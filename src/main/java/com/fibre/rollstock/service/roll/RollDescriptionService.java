package com.fibre.rollstock.service.roll;

import java.util.List;

import com.fibre.rollstock.model.RollDescription;

public interface RollDescriptionService {

	/**
	 * Creates a new roll description record. 
	 * @param description
	 * @return id of the newly created record
	 */
	public long create(RollDescription description);
	
	/**
	 * Modifies an existing roll description record.
	 * @param description
	 */
	public void update(RollDescription description);
	
	/**
	 * Removes a roll description record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves roll description records.
	 * @return list of roll description records
	 */
	public List<RollDescription> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
