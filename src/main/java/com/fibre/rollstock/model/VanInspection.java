package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class VanInspection implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private Trucker trucker;
	private String truckNumber;
	private String vanNumber;
	private String remarks;
	private Worker inspectedBy;
	private Timestamp dateCreated;
	
	public VanInspection() {}
	
	public VanInspection(long id, Trucker trucker, String truckNumber, String vanNumber, String remarks, Worker inspectedBy, Timestamp dateCreated) {
		super();
		this.id = id;
		this.trucker = trucker;
		this.truckNumber = truckNumber;
		this.vanNumber = vanNumber;
		this.dateCreated = dateCreated;
		this.remarks = remarks;
		this.inspectedBy = inspectedBy;
	}
	
	public VanInspection(Trucker trucker, String truckNumber, String vanNumber, String remarks, Worker inspectedBy, Timestamp dateCreated) {
		super();
		this.trucker = trucker;
		this.truckNumber = truckNumber;
		this.vanNumber = vanNumber;
		this.dateCreated = dateCreated;
		this.remarks = remarks;
		this.inspectedBy = inspectedBy;
	}
}
