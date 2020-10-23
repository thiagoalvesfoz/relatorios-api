package br.com.desbravador.projetoacelera.relatorio.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.relatorio.domain.Relatorio;
import br.com.desbravador.projetoacelera.relatorio.domain.repository.RelatorioRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class RelatorioService extends DefaultService<Relatorio, RelatorioRepository> {

}
