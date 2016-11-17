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

import com.fibre.rollstock.model.Trucker;
import com.fibre.rollstock.service.trucker.TruckerService;

@RestController
@RequestMapping("/api_truckers")
public class TruckerResources {

	@Autowired
	private TruckerService truckerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Trucker> findAll() {
		return truckerService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return truckerService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Trucker trucker) {
		return truckerService.create(trucker);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Trucker trucker) {
		truckerService.update(trucker);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		truckerService.delete(id);
	}
}
