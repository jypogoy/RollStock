package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class Bin implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private double length;
	private double width;
	private double height;
	private Warehouse warehouse;
	private List<Roll> rolls;
	private Timestamp dateCreated;
	
	public Bin() {}
	
	public Bin(long id, String name, double length, double width, double height, Warehouse warehouse, List<Roll> rolls, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.length = length;
		this.width = width;
		this.height = height;
		this.warehouse = warehouse;
		this.rolls = rolls;
		this.dateCreated = dateCreated;
	}

	public Bin(String name, double length, double width, double height, Warehouse warehouse, List<Roll> rolls, Timestamp dateCreated) {
		super();
		this.name = name;
		this.length = length;
		this.width = width;
		this.height = height;
		this.warehouse = warehouse;
		this.rolls = rolls;
		this.dateCreated = dateCreated;
	}
}
