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

import com.fibre.rollstock.model.VanInspection;
import com.fibre.rollstock.service.van.VanInspectionService;

@RestController
@RequestMapping("/api_van_inspections")
public class VanInspectionResources {

	@Autowired
	private VanInspectionService vanInspectionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<VanInspection> findAll() {
		return vanInspectionService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return vanInspectionService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody VanInspection inspection) {
		return vanInspectionService.create(inspection);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody VanInspection inspection) {
		vanInspectionService.update(inspection);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		vanInspectionService.delete(id);
	}
}
