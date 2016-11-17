package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Receipt implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;

	private long id;	
	private Supplier supplier;
	private Trucker trucker;
	private Worker preparedBy;
	private Worker checkedBy;
	private Worker notedBy;
	private Timestamp dateCreated;
	private Timestamp dateChecked;
	private Timestamp dateNoted;
	private double rollCount;
	private double bodyCount;
	private double coverCount;
	private double padCount;
	private double totalWeight;
	
	public Receipt() {}
	
	public Receipt(long id, Supplier supplier, Trucker trucker, Worker preparedBy, Worker checkedBy, Worker notedBy, 
			Timestamp dateCreated, Timestamp dateChecked, Timestamp dateNoted, 
			double rollCount, double bodyCount, double coverCount, double padCount, double totalWeight) {
		super();
		this.id = id;
		this.supplier = supplier;
		this.trucker = trucker;
		this.preparedBy = preparedBy;
		this.checkedBy = checkedBy;
		this.notedBy = notedBy;
		this.dateCreated = dateCreated;
		this.dateChecked = dateChecked;
		this.dateNoted = dateNoted;
		this.rollCount = rollCount;
		this.bodyCount = bodyCount;
		this.coverCount = coverCount;
		this.padCount = padCount;
		this.totalWeight = totalWeight;
	}
	
	public Receipt(Supplier supplier, Trucker trucker, Worker preparedBy, Worker checkedBy, Worker notedBy, 
			Timestamp dateCreated, Timestamp dateChecked, Timestamp dateNoted, 
			double rollCount, double bodyCount, double coverCount, double padCount, double totalWeight) {
		super();
		this.supplier = supplier;
		this.trucker = trucker;
		this.preparedBy = preparedBy;
		this.checkedBy = checkedBy;
		this.notedBy = notedBy;
		this.dateCreated = dateCreated;
		this.dateChecked = dateChecked;
		this.dateNoted = dateNoted;
		this.rollCount = rollCount;
		this.bodyCount = bodyCount;
		this.coverCount = coverCount;
		this.padCount = padCount;
		this.totalWeight = totalWeight;
	}
}
