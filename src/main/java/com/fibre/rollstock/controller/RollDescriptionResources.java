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

import com.fibre.rollstock.model.RollDescription;
import com.fibre.rollstock.service.roll.RollDescriptionService;

@RestController
@RequestMapping("/api_descriptions")
public class RollDescriptionResources {

	@Autowired
	private RollDescriptionService descriptionService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<RollDescription> findAll() {
		return descriptionService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return descriptionService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody RollDescription description) {
		return descriptionService.create(description);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody RollDescription description) {
		descriptionService.update(description);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		descriptionService.delete(id);
	}
}
