package com.fibre.rollstock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fibre.rollstock.model.Supplier;
import com.fibre.rollstock.service.supplier.SupplierService;

@RestController
@RequestMapping("/api_suppliers")
public class SupplierResources {

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Supplier> findAll() {
		return supplierService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return supplierService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Supplier supplier) {
		return supplierService.create(supplier);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Supplier supplier) {
		supplierService.update(supplier);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		supplierService.delete(id);
	}
}
