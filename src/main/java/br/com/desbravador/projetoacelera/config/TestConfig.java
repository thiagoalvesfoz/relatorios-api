package br.com.desbravador.projetoacelera.config;

import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.email.impl.MockEmailService;
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
