package service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private UsuarioRepository repositorio;
		
	public UsuarioService(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}
	
}
