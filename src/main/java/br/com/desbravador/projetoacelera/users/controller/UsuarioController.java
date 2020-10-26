package br.com.desbravador.projetoacelera.users.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.dto.UsuarioDto;
import br.com.desbravador.projetoacelera.users.dto.input.UsuarioInput;
import br.com.desbravador.projetoacelera.users.service.UsuarioService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController extends DefaultController<Usuario, UsuarioService> {	

	@PostMapping
	public UsuarioDto save(@RequestBody UsuarioInput cadastro) {
		Usuario usuario = service.save(cadastro.toEntity());
		UsuarioDto dto = new UsuarioDto(usuario);
		return dto;
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDto update(@PathVariable Long id, @RequestBody Usuario inputUser) {
		Usuario usuario = service.update(id, inputUser);		
		return new UsuarioDto(usuario);
	}
}
