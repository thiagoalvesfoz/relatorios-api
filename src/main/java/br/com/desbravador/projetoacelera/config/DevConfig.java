package br.com.desbravador.projetoacelera.config;

import br.com.desbravador.projetoacelera.email.EmailService;
import br.com.desbravador.projetoacelera.email.impl.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@Profile("dev")
public class DevConfig {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    @Autowired
    public DevConfig(MailSender mailSender, JavaMailSender javaMailSender) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService(mailSender, javaMailSender);
    }
}
