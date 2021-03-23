package br.com.desbravador.projetoacelera.config;

import com.nulabinc.zxcvbn.Zxcvbn;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Zxcvbn zxcvbn() {
		return new Zxcvbn();
	}
}
