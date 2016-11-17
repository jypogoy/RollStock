package com.fibre.rollstock.dao.file;

import java.util.List;

import com.fibre.rollstock.model.ElectronicFile;

/**
 * This interface declares all known activities in handling file records.
 * @author jeffrey.pogoy
 *
 */
public interface FileDAO {

	/**
	 * Creates a new file record. 
	 * @param file
	 * @return id of the newly created record
	 */
	public long create(ElectronicFile file);
	
	/**
	 * Modifies an existing file record.
	 * @param file
	 */
	public void update(ElectronicFile file);
	
	/**
	 * Removes a file record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves file records. 
	 * @return list of file records
	 */
	public List<ElectronicFile> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
