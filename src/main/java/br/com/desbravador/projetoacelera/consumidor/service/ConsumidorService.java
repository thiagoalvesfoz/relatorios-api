package br.com.desbravador.projetoacelera.consumidor.service;

import org.springframework.stereotype.Service;

import br.com.desbravador.projetoacelera.consumidor.domain.Consumidor;
import br.com.desbravador.projetoacelera.consumidor.domain.repository.ConsumidorRepository;
import br.com.desbravador.projetoacelera.web.service.DefaultService;

@Service
public class ConsumidorService extends DefaultService<Consumidor, ConsumidorRepository> {

}
