package br.com.desbravador.projetoacelera.fontededados.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.fontededados.domain.FonteDeDados;
import br.com.desbravador.projetoacelera.fontededados.service.FonteDeDadosService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;

@RestController
@RequestMapping("api/fontededados")
public class FonteDeDadosController extends DefaultController<FonteDeDados, FonteDeDadosService> {

}
