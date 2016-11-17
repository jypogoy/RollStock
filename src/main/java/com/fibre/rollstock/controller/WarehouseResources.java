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

import com.fibre.rollstock.model.Warehouse;
import com.fibre.rollstock.service.warehouse.WarehouseService;

@RestController
@RequestMapping("/api_warehouses")
public class WarehouseResources {

	@Autowired
	private WarehouseService warehouseService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Warehouse> findAll() {
		return warehouseService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return warehouseService.count();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Warehouse findById(@PathVariable long id) {
		return warehouseService.findById(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Warehouse warehouse) {
		return warehouseService.create(warehouse);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Warehouse warehouse) {
		warehouseService.update(warehouse);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		warehouseService.delete(id);
	}
}
