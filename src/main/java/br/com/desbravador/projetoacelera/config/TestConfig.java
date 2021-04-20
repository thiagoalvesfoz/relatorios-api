package br.com.desbravador.projetoacelera.config;

import br.com.desbravador.projetoacelera.sender.EmailService;
import br.com.desbravador.projetoacelera.sender.impl.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

}
