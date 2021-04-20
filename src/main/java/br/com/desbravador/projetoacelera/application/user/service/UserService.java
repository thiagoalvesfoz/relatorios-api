package br.com.desbravador.projetoacelera.application.user.service;

import br.com.desbravador.projetoacelera.application.auth.stream.UserSecurity;
import br.com.desbravador.projetoacelera.application.auth.service.AuthService;
import br.com.desbravador.projetoacelera.application.user.entity.User;
import br.com.desbravador.projetoacelera.application.user.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.AuthorizationException;
import br.com.desbravador.projetoacelera.web.exception.BusinessRuleException;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

	private final UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public User save(User newAccount) {

		UserSecurity user = AuthService.authenticated();

		if (user == null || !user.hasRole("ROLE_ADMIN")) {
			throw new AuthorizationException("Access Denied!");
		}
		
		repository.findByEmail(newAccount.getEmail()).ifPresent( function -> {
			throw new BusinessRuleException("E-mail already registered!"); 
		});

		newAccount.setPassword(RandomString.make(8));
		return repository.save(newAccount);
	}

	@Transactional
	public User findOne(Long id) {

		UserSecurity user = AuthService.authenticated();

		if (user == null || !user.hasRole("ROLE_ADMIN") && !id.equals(user.getId())) {
			throw new AuthorizationException("Access Denied!");
		}

		return repository
				.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("User not found!") );
	}

	@Transactional
	public User update(Long id, User inputUser) {

		User user = this.findOne(id);

		if (!user.getName().equals(inputUser.getName()) && inputUser.getName() != null) {
			user.setName(inputUser.getName());
		}

		if (user.isAdmin() != inputUser.isAdmin()) {
			user.setAdmin(inputUser.isAdmin());
		}

		user.setUpdatedAt(Instant.now());

		return repository.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {

		UserSecurity user = AuthService.authenticated();

		if (user == null || !user.isAdmin()) {
			throw new AuthorizationException("Access Denied!");
		}

		return repository.findAll();
	}
}
