package br.com.desbravador.projetoacelera.controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.dto.UsuarioDto;
import br.com.desbravador.projetoacelera.dto.input.UsuarioInput;
import br.com.desbravador.projetoacelera.model.entities.Usuario;
import br.com.desbravador.projetoacelera.service.UsuarioService;


@RequestMapping("api/usuarios")
@RestController
public class UsuarioController {
	
	private final UsuarioService servico;
	
	private final ModelMapper modelMapper;
	
	public UsuarioController(UsuarioService servico, ModelMapper modelMapper) {
		this.servico = servico;
		this.modelMapper = modelMapper;
	}
		
	@GetMapping
	public List<UsuarioDto> all() {
		List<Usuario> usuarios = this.servico.listarUsuarios();
		return this.toCollectionModel(usuarios);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto criarUsuario(@RequestBody UsuarioInput usuarioInput) {
		Usuario usuario = this.toEntity(usuarioInput);
		Usuario noUsuario = this.servico.criarUsuario(usuario);
		return this.toModel(noUsuario);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> pesquisarUsuario(@PathVariable Integer id) {
		
		Optional<Usuario> usuario = this.servico.buscarUsuario(id);
		
		if (usuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		UsuarioDto dto = this.toModel(usuario.get());
		
		return ResponseEntity.ok(dto);
	}
	
	private UsuarioDto toModel(Usuario usuario) {
		return this.modelMapper.map(usuario, UsuarioDto.class);
	}
	
	private Usuario toEntity(UsuarioInput dto) {
		return this.modelMapper.map(dto, Usuario.class);
	}
	
	private List<UsuarioDto> toCollectionModel(List<Usuario> usuarios){
		return usuarios.stream().map(this::toModel).collect(Collectors.toList());
	}
}
