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

import com.fibre.rollstock.model.Worker;
import com.fibre.rollstock.service.worker.WorkerService;

@RestController
@RequestMapping("/api_workers")
public class WorkerResources {

	@Autowired
	private WorkerService workerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Worker> findAll() {
		return workerService.findAll();
	}
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	public int count() {
		return workerService.count();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Worker worker) {
		return workerService.create(worker);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Worker worker) {
		workerService.update(worker);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		workerService.delete(id);
	}
}
