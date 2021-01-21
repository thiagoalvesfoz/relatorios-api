package br.com.desbravador.projetoacelera.email.impl;

import br.com.desbravador.projetoacelera.email.AbstractEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulating email sending...");
        LOG.info(msg.toString());
        LOG.info("Simulation completed, email sending!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Simulating html email sending...");
        LOG.info(msg.toString());
        LOG.info("Simulation completed, email sending!");
    }
}
