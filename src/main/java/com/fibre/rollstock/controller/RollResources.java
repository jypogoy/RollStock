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

import com.fibre.rollstock.model.Roll;
import com.fibre.rollstock.service.roll.RollService;

@RestController
@RequestMapping("/api_rolls")
public class RollResources {

	@Autowired
	private RollService rollService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Roll> findAll() {
		return rollService.findAll();
	}
	
	@RequestMapping(value="/for_storage", method=RequestMethod.GET)
	public List<Roll> findForStorage() {
		return rollService.findForStorage();
	}
	
	@RequestMapping(value="/{receiptId}", method=RequestMethod.GET)
	public List<Roll> findByReceipt(@PathVariable long receiptId) {
		return rollService.findByReceipt(receiptId);
	}
	
	@RequestMapping(value="/bin/{binId}", method=RequestMethod.GET)
	public List<Roll> findByBin(@PathVariable long binId) {
		return rollService.findByBin(binId);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Roll roll) {
		return rollService.create(roll);
	}
	
	@RequestMapping(value="/multiple/", method=RequestMethod.POST)
	public void createMultiple(@RequestBody List<Roll> rolls) {
		rollService.createMultiple(rolls);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Roll roll) {
		rollService.update(roll);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		rollService.delete(id);
	}
	
	@RequestMapping(value="/by_receipt/{receiptId}", method=RequestMethod.DELETE)
	public @ResponseBody void deleteByReceipt(@PathVariable long receiptId) {
		rollService.deleteByReceipt(receiptId);
	}
}
