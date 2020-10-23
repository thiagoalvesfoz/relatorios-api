package br.com.desbravador.projetoacelera.produto.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.produto.domain.Produto;
import br.com.desbravador.projetoacelera.produto.domain.repository.ProdutoRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class ProdutoService extends DefaultService<Produto, ProdutoRepository>{

}
