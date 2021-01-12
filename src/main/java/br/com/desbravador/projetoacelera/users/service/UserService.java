package br.com.desbravador.projetoacelera.users.service;

import br.com.desbravador.projetoacelera.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.User;
import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.web.exception.BusinessRuleException;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class UserService extends DefaultService<User, UserRepository>{

	@Autowired
	private EmailService emailService;

	public User update(Long id, User inputUser) {
		
		User usuario = this.findOne(id);
		
		if (!usuario.getName().equals(inputUser.getName()) && inputUser.getName() != null) {
			usuario.setName(inputUser.getName());
		}
		
		if (usuario.isAdmin() != inputUser.isAdmin()) {
			usuario.setAdmin(inputUser.isAdmin());
		}
		
		return this.repository.save(usuario);
	}

	@Override
	public User findOne(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("User not found!") );
	}

	@Override
	public User save(User entity) {
		
		super.repository.findByEmail(entity.getEmail()).ifPresent( function -> { 
			throw new BusinessRuleException("E-mail already registered!"); 
		});	
		
		entity = super.save(entity);
		
		//Send  Email Confirmation
		emailService.sendAccountRegistration(entity);
		
		return entity;
	}
	
	
	
}
