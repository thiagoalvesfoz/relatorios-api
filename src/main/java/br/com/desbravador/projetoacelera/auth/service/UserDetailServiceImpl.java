package br.com.desbravador.projetoacelera.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.repository.UserRepository;
import br.com.desbravador.projetoacelera.users.dto.UserDto;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var usuario = repository
			.findByEmail(email)
			.orElseThrow( () -> new UsernameNotFoundException("User not found.") );
		
		return User
				.builder()
				.username(usuario.getEmail())
				.password(usuario.getPassword())
				.roles(usuario.getAuthority())	
				.disabled(!usuario.isActive())
				.build();
	}
	
	
	public UserDto getUser(String email) {
		
		var usuario = repository.findByEmail(email).get();
		
		return new UserDto(usuario);
	}

}