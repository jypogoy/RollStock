package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Supplier implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String email;
	private String phoneMobile;
	private String phoneLandline;
	private String address;
	private Timestamp dateCreated;
	
	public Supplier() {}
	
	public Supplier(long id, String name, String email, String mobile, String phone, String address, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneMobile = mobile;
		this.phoneLandline = phone;		
		this.address = address;
		this.dateCreated = dateCreated;
	}
	
	public Supplier(String name, String email, String mobile, String phone, String address, Timestamp dateCreated) {
		super();
		this.name = name;
		this.email = email;
		this.phoneMobile = mobile;
		this.phoneLandline = phone;
		this.address = address;
		this.dateCreated = dateCreated;
	}
}
