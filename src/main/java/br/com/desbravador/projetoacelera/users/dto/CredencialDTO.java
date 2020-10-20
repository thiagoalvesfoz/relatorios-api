package br.com.desbravador.projetoacelera.users.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.desbravador.projetoacelera.users.domain.Usuario;


public class CredencialDTO implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	public CredencialDTO(Usuario usuario) {
	    this.email = usuario.getEmail();
	    this.password = usuario.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
