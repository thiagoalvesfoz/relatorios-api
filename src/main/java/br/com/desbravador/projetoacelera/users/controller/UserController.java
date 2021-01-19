package br.com.desbravador.projetoacelera.users.controller;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.dto.UserDto;
import br.com.desbravador.projetoacelera.users.dto.input.UserInput;
import br.com.desbravador.projetoacelera.users.dto.input.UserUpdate;
import br.com.desbravador.projetoacelera.users.service.UserService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/users")
public class UserController extends DefaultController<User, UserService> {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDto save(@RequestBody @Valid UserInput newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		User user = service.save(newUser.toEntity());
		return new UserDto(user);
	}

	@PutMapping("{id}")
	public ResponseEntity<User> update(@PathVariable Long id,@Valid @RequestBody UserUpdate body ) {
		body.setPassword(passwordEncoder.encode(body.getPassword()));
		User user = service.update(id, body.toEntity());
		return ResponseEntity.ok().body(user);
	}

	@Override
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("{id}")
	public ResponseEntity<User> getOne(@PathVariable Long id) {
		return super.getOne(id);
	}
}
