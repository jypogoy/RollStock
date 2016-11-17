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

import com.fibre.rollstock.model.VanImage;
import com.fibre.rollstock.service.van.VanImageService;

@RestController
@RequestMapping("/api_van_mages")
public class VanImageResources {

	@Autowired
	private VanImageService vanImageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<VanImage> findAll() {
		return vanImageService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return vanImageService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody VanImage image) {
		return vanImageService.create(image);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody VanImage image) {
		vanImageService.update(image);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		vanImageService.delete(id);
	}
}
