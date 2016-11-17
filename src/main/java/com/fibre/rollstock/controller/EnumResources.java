package com.fibre.rollstock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fibre.rollstock.common.RollPart;

@RestController
@RequestMapping("/api_enums")
public class EnumResources {

	@RequestMapping(value="/parts", method=RequestMethod.GET)
	public RollPart[] getParts() {
		return RollPart.values();
	}
}
