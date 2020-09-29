package br.com.desbravador.projetoacelera.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController

public class UserController {
	@GetMapping
	public String Hello() {
		return "hello";
	}
}
