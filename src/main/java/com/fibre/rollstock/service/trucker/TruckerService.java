package com.fibre.rollstock.service.trucker;

import java.util.List;

import com.fibre.rollstock.model.Trucker;

public interface TruckerService {

	/**
	 * Creates a new trucker record. 
	 * @param trucker
	 * @return id of the newly created record
	 */
	public long create(Trucker trucker);
	
	/**
	 * Modifies an existing trucker record.
	 * @param trucker
	 */
	public void update(Trucker trucker);
	
	/**
	 * Removes a trucker record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves trucker records. May provide filtered values using a keyword.
	 * @return list of trucker records
	 */
	public List<Trucker> findAll();
	
	/**
	 * Retrieves a trucker record using a specific id.
	 * @param id
	 * @return trucker record
	 */
	public Trucker findById(long id);
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
