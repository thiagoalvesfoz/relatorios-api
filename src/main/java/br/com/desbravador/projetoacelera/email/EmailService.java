package br.com.desbravador.projetoacelera.email;

import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendAccountRegistration(User account, String setPasswordLink);
    void sendEmail(SimpleMailMessage msg);

    void sendHtmlAccountRegistration(User account, String setPasswordLink);
    void sendHtmlEmail(MimeMessage msg);

    void sendResetPasswordEmail(User user, String link);
    void sendHtmlResetPasswordEmail(User user, String link);
}
