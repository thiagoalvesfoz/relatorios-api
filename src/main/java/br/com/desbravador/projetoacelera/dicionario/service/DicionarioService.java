package br.com.desbravador.projetoacelera.dicionario.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.dicionario.domain.Dicionario;
import br.com.desbravador.projetoacelera.dicionario.domain.repository.DicionarioRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class DicionarioService extends DefaultService<Dicionario, DicionarioRepository> {

}
