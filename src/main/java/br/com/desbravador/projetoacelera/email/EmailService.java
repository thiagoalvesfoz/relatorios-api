package br.com.desbravador.projetoacelera.email;

import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendAccountRegistration(User user);
    void sendEmail(SimpleMailMessage msg);

    void sendHtmlAccountRegistration(User user);
    void sendHtmlEmail(MimeMessage msg);

}
