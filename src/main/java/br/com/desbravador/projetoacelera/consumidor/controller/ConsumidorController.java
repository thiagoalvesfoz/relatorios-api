package br.com.desbravador.projetoacelera.consumidor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desbravador.projetoacelera.consumidor.domain.Consumidor;
import br.com.desbravador.projetoacelera.consumidor.service.ConsumidorService;
import br.com.desbravador.projetoacelera.web.controller.DefaultController;

@RestController
@RequestMapping("api/consumidores")
public class ConsumidorController extends DefaultController<Consumidor, ConsumidorService> {

}
