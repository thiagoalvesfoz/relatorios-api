package br.com.desbravador.projetoacelera.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.model.entities.Usuario;

@RequestMapping("api/usuarios")
@RestController
public class UsuarioController {
	private List<Usuario> bancoDeDados = new ArrayList<>();
	
	@GetMapping
	public List<Usuario> all() {
		return this.bancoDeDados;
	}

	@PostMapping
	public Usuario criarUsuario(@RequestBody Usuario entrada) {
		int idContador = this.bancoDeDados.size();
		entrada.setId(idContador + 1);
		this.bancoDeDados.add(entrada);
		return entrada;
		
	}
}
