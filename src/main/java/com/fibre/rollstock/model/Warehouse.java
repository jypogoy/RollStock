package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class Warehouse implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	private String address;
	private List<Bin> bins;
	private Timestamp dateCreated;
	
	public Warehouse() {}
	
	public Warehouse(long id, String name, String description, String address, List<Bin> bins, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.bins = bins;
		this.dateCreated = dateCreated;
	}
	
	public Warehouse(String name, String description, String address, List<Bin> bins, Timestamp dateCreated) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.bins = bins;
		this.dateCreated = dateCreated;
	}
}
