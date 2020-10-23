package br.com.desbravador.projetoacelera.dicionario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.dicionario.domain.Dicionario;
import br.com.desbravador.projetoacelera.dicionario.service.DicionarioService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;

@RestController
@RequestMapping("api/dicionarios")
public class DicionarioController extends DefaultController<Dicionario, DicionarioService> {

}
