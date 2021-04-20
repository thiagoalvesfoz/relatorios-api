package br.com.desbravador.projetoacelera.application.user.controller;

import br.com.desbravador.projetoacelera.config.Utility;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import br.com.desbravador.projetoacelera.application.user.dto.UserDto;
import br.com.desbravador.projetoacelera.application.user.dto.input.UserInput;
import br.com.desbravador.projetoacelera.application.user.dto.input.UserUpdate;
import br.com.desbravador.projetoacelera.sender.EmailService;
import br.com.desbravador.projetoacelera.application.user.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/users")
public class UserController {

	private final UserService service;
	private final EmailService emailService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserController(BCryptPasswordEncoder passwordEncoder, EmailService emailService, UserService service) {
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		var users = service.findAll().stream().map(UserDto::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(users);
	}


	@GetMapping("{id}")
	public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
		var result = new UserDto(service.findOne(id));
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<UserDto> createNewAccount(@RequestBody @Valid UserInput newUser, HttpServletRequest request) {
		User accountUser = newUser.toEntity();
		String token = RandomString.make(30);

		accountUser.setToken(token);
		accountUser = service.save(accountUser);

		String setPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
		emailService.sendHtmlAccountRegistration(accountUser, setPasswordLink);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(accountUser.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(accountUser));
	}

	@PutMapping("{id}")
	public ResponseEntity<UserDto> updateAccountInformation(@PathVariable Long id,@Valid @RequestBody UserUpdate body ) {
		body.setPassword(passwordEncoder.encode(body.getPassword()));
		var user = service.update(id, body.toEntity());
		return ResponseEntity.ok().body(new UserDto(user));
	}

}
