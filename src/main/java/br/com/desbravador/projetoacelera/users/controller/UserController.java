package br.com.desbravador.projetoacelera.users.controller;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.dto.UserDto;
import br.com.desbravador.projetoacelera.users.dto.input.UserInput;
import br.com.desbravador.projetoacelera.users.dto.input.UserUpdate;
import br.com.desbravador.projetoacelera.users.service.UserService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;


@RestController
@RequestMapping("api/users")
public class UserController extends DefaultController<User, UserService> {	

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDto save(@RequestBody @Valid UserInput newUser) {
		User usuario = service.save(newUser.toEntity());
		UserDto dto = new UserDto(usuario);
		return dto;
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> update(@PathVariable Long id,@Valid @RequestBody UserUpdate body ) {
		User usuario = service.update(id, body.toEntity());
		return ResponseEntity.ok().body(usuario);
		
	}
}
