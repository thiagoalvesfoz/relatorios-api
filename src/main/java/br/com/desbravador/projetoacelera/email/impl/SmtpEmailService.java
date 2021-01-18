package br.com.desbravador.projetoacelera.email.impl;

import br.com.desbravador.projetoacelera.email.AbstractEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    @Autowired
    public SmtpEmailService(MailSender mailSender, JavaMailSender javaMailSender) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Sending email...");
        mailSender.send(msg);
        LOG.info("Email successfully sent!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Sending HTML email...");
        javaMailSender.send(msg);
        LOG.info("Email successfully sent!");
    }
}
