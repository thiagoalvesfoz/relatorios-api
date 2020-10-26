package br.com.desbravador.projetoacelera.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.desbravador.projetoacelera.users.domain.Usuario;


public class CredencialDTO extends Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	public CredencialDTO(Usuario usuario) {
		super(usuario);
	    this.email = usuario.getEmail();
	    this.password = usuario.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this);
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
		return !super.isBloqueado();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.isAtivo();
	}
}
