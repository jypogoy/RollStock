package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Customer implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String email;
	private String phoneLandline;
	private String phoneMobile;
	private String address;
	private Timestamp dateCreated;
	
	public Customer() {}
	
	public Customer(long id, String name, String email, String phoneLandline, String phoneMobile, String address, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneLandline = phoneLandline;
		this.phoneMobile = phoneMobile;
		this.address = address;
		this.dateCreated = dateCreated;
	}
	
	public Customer(String name, String email, String phoneLandline, String phoneMobile, String address, Timestamp dateCreated) {
		super();
		this.name = name;
		this.email = email;
		this.phoneLandline = phoneLandline;
		this.phoneMobile = phoneMobile;
		this.address = address;
		this.dateCreated = dateCreated;
	}
}
