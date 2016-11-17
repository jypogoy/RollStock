package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Roll implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String number;
	private RollDescription description;
	private double grade;
	private double sized;
	private double weight;
	private double lineal;
	private String remarks;
	private Part part;
	private Receipt receipt;
	private Ticket ticket;
	private Storage storage;
	private Timestamp dateCreated;
	
	public Roll() {}
	
	public Roll(long id, String number, RollDescription description, double grade, double sized, double weight, double lineal, String remarks, 
			Part part, Receipt receipt, Ticket ticket, Storage storage, Timestamp dateCreated) {
		super();
		this.id = id;
		this.number = number;
		this.description = description;
		this.grade = grade;
		this.sized = sized;
		this.weight = weight;
		this.lineal = lineal;
		this.remarks = remarks;
		this.part = part;
		this.receipt = receipt;
		this.ticket = ticket;
		this.storage = storage;
		this.dateCreated = dateCreated;
	}
	
	public Roll(String number, RollDescription description, double grade, double sized, double weight, double lineal, String remarks, 
			Part part, Receipt receipt, Ticket ticket, Storage storage, Timestamp dateCreated) {
		super();
		this.number = number;
		this.description = description;
		this.grade = grade;
		this.sized = sized;
		this.weight = weight;
		this.lineal = lineal;
		this.remarks = remarks;
		this.part = part;
		this.receipt = receipt;
		this.ticket = ticket;
		this.storage = storage;
		this.dateCreated = dateCreated;
	}
}
