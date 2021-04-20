package br.com.desbravador.projetoacelera.config;

import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {

	@Bean
	public Zxcvbn zxcvbn() {
		return new Zxcvbn();
	}
}
