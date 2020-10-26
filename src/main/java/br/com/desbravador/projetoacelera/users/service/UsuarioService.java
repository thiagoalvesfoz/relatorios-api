package br.com.desbravador.projetoacelera.users.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.domain.repository.UsuarioRepository;
import br.com.desbravador.projetoacelera.web.exception.ResourceNotFoundException;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class UsuarioService extends DefaultService<Usuario, UsuarioRepository>{

	public Usuario update(Long id, Usuario inputUser) {
		
		Usuario usuario = this.findOne(id);
		
		if (!usuario.getNome().equals(inputUser.getNome()) && inputUser.getNome() != null) {
			usuario.setNome(inputUser.getNome());
		}
		if (usuario.isAdministrador() != inputUser.isAdministrador()) {
			usuario.setAdministrador(inputUser.isAdministrador());
		}
		
		return this.repository.save(usuario);
	}

	@Override
	public Usuario findOne(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResourceNotFoundException("Usuario não encontrado!") );
	}	
	
}
