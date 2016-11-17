package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class VanImage implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private VanInspection vanInspection;
	private ElectronicFile electronicFile;
	private Timestamp dateTaken;
	private boolean isBefore;
	
	public VanImage() {}
	
	public VanImage(long id, VanInspection vanInspection, ElectronicFile electronicFile, Timestamp dateTaken, boolean isBefore) {
		super();
		this.id = id;
		this.vanInspection = vanInspection;
		this.electronicFile = electronicFile;
		this.dateTaken = dateTaken;
		this.isBefore = isBefore;
	}

	public VanImage(VanInspection vanInspection, ElectronicFile electronicFile, Timestamp dateTaken, boolean isBefore) {
		super();
		this.vanInspection = vanInspection;
		this.electronicFile = electronicFile;
		this.dateTaken = dateTaken;
		this.isBefore = isBefore;
	}
}
