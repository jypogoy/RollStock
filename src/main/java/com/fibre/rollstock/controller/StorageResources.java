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

import com.fibre.rollstock.model.Storage;
import com.fibre.rollstock.service.storage.StorageService;

@RestController
@RequestMapping("/api_storages")
public class StorageResources {

	@Autowired
	private StorageService storageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Storage> findAll() {
		return storageService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return storageService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Storage storage) {
		return storageService.create(storage);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Storage storage) {
		storageService.update(storage);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		storageService.delete(id);
	}
}
