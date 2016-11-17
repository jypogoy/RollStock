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

import com.fibre.rollstock.model.Receipt;
import com.fibre.rollstock.service.receipt.ReceiptService;

@RestController
@RequestMapping("/api_receipts")
public class ReceiptResources {

	@Autowired
	private ReceiptService receiptService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Receipt> findAll(String keyword) {
		return receiptService.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Receipt findById(@PathVariable long id) {
		return receiptService.findById(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create(@RequestBody Receipt receipt) {
		return receiptService.create(receipt);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Receipt receipt) {
		receiptService.update(receipt);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		receiptService.delete(id);
	}
}
