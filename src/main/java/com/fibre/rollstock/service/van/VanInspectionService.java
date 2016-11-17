package com.fibre.rollstock.service.van;

import java.util.List;

import com.fibre.rollstock.model.VanInspection;

public interface VanInspectionService {

	/**
	 * Creates a new vanInspection record. 
	 * @param inspection
	 * @return id of the newly created record
	 */
	public long create(VanInspection inspection);
	
	/**
	 * Modifies an existing vanInspection record.
	 * @param inspection
	 */
	public void update(VanInspection inspection);
	
	/**
	 * Removes a inspection record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves inspection records.
	 * @return list of inspection records
	 */
	public List<VanInspection> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
