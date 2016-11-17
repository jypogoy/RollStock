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

import com.fibre.rollstock.model.Bin;
import com.fibre.rollstock.service.bin.BinService;

@RestController
@RequestMapping("/api_bins")
public class BinResources {

	@Autowired
	private BinService binService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Bin> findAll() {
		return binService.findAll();
	}
	
	@RequestMapping(value="/{warehouseId}", method=RequestMethod.GET)
	public List<Bin> findByWarehouse(@PathVariable long warehouseId) {
		return binService.findByWarehouse(warehouseId);
	}
	
	@RequestMapping(value="/available/{warehouseId}", method=RequestMethod.GET)
	public List<Bin> findAvailableByWarehouse(@PathVariable long warehouseId) {
		return binService.findAvailableByWarehouse(warehouseId);
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return binService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Bin bin) {
		return binService.create(bin);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Bin bin) {
		binService.update(bin);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		binService.delete(id);
	}
}
