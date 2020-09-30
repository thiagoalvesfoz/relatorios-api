package br.com.desbravador.projetoacelera.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.model.entities.Usuario;
import br.com.desbravador.projetoacelera.repository.UsuarioRepository;

@RequestMapping("api/usuarios")
@RestController
public class UsuarioController {
	
	private UsuarioRepository repositorio;
	
	public UsuarioController(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}
		
	@GetMapping
	public List<Usuario> all() {
		return this.repositorio.findAll();
	}

	@PostMapping
	public Usuario criarUsuario(@RequestBody Usuario entrada) {
		return this.repositorio.save(entrada);
	}
	
	@GetMapping("/{id}")
	public Usuario pesquisarUsuario(@PathVariable Integer id) {
		return this.repositorio.findById(id).get();
	}
}
