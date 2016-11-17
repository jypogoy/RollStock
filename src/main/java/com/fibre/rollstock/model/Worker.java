package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Worker implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;	
	private String email;
	private String phoneMobile;
	private String phoneLandline;
	private String address;
	private WorkerRole workerRole;
	private Timestamp dateCreated;
	
	public Worker() {}
	
	public Worker(long id, String name, String address, String phoneMobile, String phoneLandline, String email, WorkerRole workerRole, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneMobile = phoneMobile;
		this.phoneLandline = phoneLandline;
		this.email = email;
		this.workerRole = workerRole;
		this.dateCreated = dateCreated;
	}
	
	public Worker(String name, String address, String phoneMobile, String phoneLandline, String email, WorkerRole workerRole, Timestamp dateCreated) {
		super();
		this.name = name;
		this.address = address;
		this.phoneMobile = phoneMobile;
		this.phoneLandline = phoneLandline;
		this.email = email;
		this.workerRole = workerRole;
		this.dateCreated = dateCreated;
	}
}
