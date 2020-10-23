package br.com.desbravador.projetoacelera.relatorio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.relatorio.domain.Relatorio;
import br.com.desbravador.projetoacelera.relatorio.service.RelatorioService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;

@RestController
@RequestMapping("api/relatorios")
public class RelatorioController extends DefaultController<Relatorio, RelatorioService> {

}
