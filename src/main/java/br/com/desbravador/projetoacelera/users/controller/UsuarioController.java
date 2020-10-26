package br.com.desbravador.projetoacelera.users.controller;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.dto.UsuarioDto;
import br.com.desbravador.projetoacelera.users.dto.input.UsuarioInput;
import br.com.desbravador.projetoacelera.users.dto.input.UsuarioUpdate;
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
	
	@PutMapping("{id}")
	public ResponseEntity<Usuario> update(@PathVariable Long id,@Valid @RequestBody UsuarioUpdate body ) {
		Usuario usuario = service.update(id, body.toEntity());
		return ResponseEntity.ok().body(usuario);
		
	}
}
