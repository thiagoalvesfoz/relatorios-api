package br.com.desbravador.projetoacelera.users.controller;
import br.com.desbravador.projetoacelera.config.Utility;
import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.dto.UserDto;
import br.com.desbravador.projetoacelera.users.dto.input.UserInput;
import br.com.desbravador.projetoacelera.users.dto.input.UserUpdate;
import br.com.desbravador.projetoacelera.users.service.UserService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("api/users")
public class UserController extends DefaultController<User, UserService> {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDto save(@RequestBody @Valid UserInput newUser, HttpServletRequest request) {
		User accountUser = newUser.toEntity();
		String token = RandomString.make(30);

		accountUser.setToken(token);
		accountUser = service.save(accountUser);

		String setPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
		emailService.sendHtmlAccountRegistration(accountUser, setPasswordLink);

		return new UserDto(accountUser);
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
