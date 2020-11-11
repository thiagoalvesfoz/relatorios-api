package br.com.desbravador.projetoacelera.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.domain.repository.UsuarioRepository;
import br.com.desbravador.projetoacelera.users.dto.UsuarioDto;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repository
			.findByEmail(email)
			.orElseThrow( () -> new UsernameNotFoundException("Usuario nao encontrado.") );
		
		return User
				.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.roles(usuario.getAuthority())				
				.build();
	}
	
	
	public UsuarioDto getUser(String email) {
		
		Usuario usuario = repository.findByEmail(email).get();
		
		return new UsuarioDto(usuario);
	}

}