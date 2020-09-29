package br.com.desbravador.projetoacelera.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/users")
@RestController

public class UserController {
	@GetMapping
	public String Hello() {
		return "hello";
	}
}
