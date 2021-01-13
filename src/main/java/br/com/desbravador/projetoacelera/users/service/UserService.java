package br.com.desbravador.projetoacelera.users.service;

import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.BusinessRuleException;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import br.com.desbravador.projetoacelera.web.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends DefaultService<User, UserRepository>{

	@Autowired
	private EmailService emailService;

	@Transactional
	public User update(Long id, User inputUser) {
		
		User user = this.findOne(id);
		
		if (!user.getName().equals(inputUser.getName()) && inputUser.getName() != null) {
			user.setName(inputUser.getName());
		}
		
		if (user.isAdmin() != inputUser.isAdmin()) {
			user.setAdmin(inputUser.isAdmin());
		}
		
		return super.repository.save(user);
	}

	@Override
	@Transactional
	public User findOne(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("User not found!") );
	}

	@Override
	@Transactional
	public User save(User entity) {
		
		super.repository.findByEmail(entity.getEmail()).ifPresent( function -> { 
			throw new BusinessRuleException("E-mail already registered!"); 
		});	
		
		entity = super.save(entity);
		
		//Send  Email
		emailService.sendHtmlAccountRegistration(entity);
		
		return entity;
	}

}
