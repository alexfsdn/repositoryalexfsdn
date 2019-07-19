package com.apptestunitary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

	@GetMapping(value = "/", produces = "application/json")
	public String initial() {
		return "Olá Spring Boot";
	}
}
