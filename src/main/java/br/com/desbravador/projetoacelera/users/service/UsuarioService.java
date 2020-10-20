package br.com.desbravador.projetoacelera.users.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.domain.repository.UsuarioRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class UsuarioService extends DefaultService<Usuario, UsuarioRepository>{	
	
}
