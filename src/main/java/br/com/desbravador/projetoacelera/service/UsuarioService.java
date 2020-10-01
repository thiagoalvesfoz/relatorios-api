package br.com.desbravador.projetoacelera.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.model.entities.Usuario;
import br.com.desbravador.projetoacelera.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository repositorio;
		
	public UsuarioService(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	
	public Usuario criarUsuario(Usuario usuario){
		return this.repositorio.save(usuario);
	}
	
	public Optional<Usuario> buscarUsuario(Integer id) {
		return this.repositorio.findById(id);
	}
	
	public List<Usuario> listarUsuarios(){
		return this.repositorio.findAll();
	}
	
}
