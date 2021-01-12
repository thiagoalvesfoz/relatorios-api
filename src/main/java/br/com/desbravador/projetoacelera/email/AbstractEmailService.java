package br.com.desbravador.projetoacelera.email;

import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendAccountRegistration(User user) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromUser(user);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromUser(User user) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Please verify your registration");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(user.toString());
        return sm;
    }
}
