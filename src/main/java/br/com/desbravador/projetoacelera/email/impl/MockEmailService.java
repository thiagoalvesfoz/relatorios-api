package br.com.desbravador.projetoacelera.email.impl;

import br.com.desbravador.projetoacelera.email.AbstractEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulationg email sending...");
        LOG.info(msg.toString());
        LOG.info("Email sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Simulationg html email sending...");
        LOG.info(msg.toString());
        LOG.info("Email sent");
    }
}
