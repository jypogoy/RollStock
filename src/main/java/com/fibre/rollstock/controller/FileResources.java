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

import com.fibre.rollstock.model.ElectronicFile;
import com.fibre.rollstock.service.file.FileService;

@RestController
@RequestMapping("/api_files")
public class FileResources {

	@Autowired
	private FileService fileService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<ElectronicFile> findAll() {
		return fileService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return fileService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody ElectronicFile file) {
		return fileService.create(file);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody ElectronicFile file) {
		fileService.update(file);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		fileService.delete(id);
	}
}
