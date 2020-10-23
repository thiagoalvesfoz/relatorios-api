package br.com.desbravador.projetoacelera.produto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.produto.domain.Produto;
import br.com.desbravador.projetoacelera.produto.service.ProdutoService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController extends DefaultController<Produto, ProdutoService> {

}
