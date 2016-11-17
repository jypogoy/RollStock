package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Storage implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Roll roll;
	private Bin bin;
	private Timestamp checkInDate;
	private Worker checkInBy;
	private Timestamp checkOutDate;
	private Worker checkOutBy;
	
	public Storage() {}
	
	public Storage(long id, Roll roll, Bin bin, Timestamp checkInDate, Worker checkInBy, Timestamp checkOutDate, Worker checkOutBy) {
		super();
		this.id = id;
		this.roll = roll;
		this.bin = bin;
		this.checkInDate = checkInDate;
		this.checkInBy = checkInBy;
		this.checkOutDate = checkOutDate;
		this.checkOutBy = checkOutBy;
	}
	
	public Storage(Roll roll, Bin bin, Timestamp checkInDate, Worker checkInBy, Timestamp checkOutDate, Worker checkOutBy) {
		super();
		this.roll = roll;
		this.bin = bin;
		this.checkInDate = checkInDate;
		this.checkInBy = checkInBy;
		this.checkOutDate = checkOutDate;
		this.checkOutBy = checkOutBy;
	}
}
