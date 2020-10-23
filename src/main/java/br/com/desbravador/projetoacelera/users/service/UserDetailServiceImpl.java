package br.com.desbravador.projetoacelera.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.domain.repository.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("email: " + email);
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

}