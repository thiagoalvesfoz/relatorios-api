package br.com.desbravador.projetoacelera.users.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.users.domain.Usuario;
import br.com.desbravador.projetoacelera.users.service.UsuarioService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController extends DefaultController<Usuario, UsuarioService> {
	

	@Override
	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		return super.save(usuario);
	}
	
	
	
}
