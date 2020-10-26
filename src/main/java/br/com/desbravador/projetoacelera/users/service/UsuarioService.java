package br.com.desbravador.projetoacelera.users.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.domain.repository.UsuarioRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class UsuarioService extends DefaultService<Usuario, UsuarioRepository>{

	public Usuario update(Long id, Usuario inputUser) {
		
		Usuario user = findOne(id);
		
		if(!inputUser.getNome().equals(user.getNome())) {
			user.setNome(inputUser.getNome());
		}
		
		if(!inputUser.getSenha().equals(user.getSenha())) {
			user.setSenha(inputUser.getSenha());
		}
		
		if(inputUser.isAdministrador() != user.isAdministrador()) {
			user.setAdministrador(inputUser.isAdministrador());
		}		
		
		return repository.saveAndFlush(user);
	}

	@Override
	public Usuario findOne(Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new RuntimeException("Usuario não encontrado!") );
	}	
	
}
