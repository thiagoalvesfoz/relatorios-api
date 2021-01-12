package br.com.desbravador.projetoacelera.email;

import br.com.desbravador.projetoacelera.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

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

    @Override
    public void sendHtmlAccountRegistration(User user) {
        try {
            MimeMessage mm = prepareMimeMessageFromUser(user);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
          sendAccountRegistration(user);
        }
    }

    protected MimeMessage prepareMimeMessageFromUser(User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(user.getEmail());
        mmh.setFrom(sender, "Desbravador Software");
        mmh.setSubject("Please verify your registration");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateRegistration(user), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateRegistration(User user) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("email/registration", context);
    }
}
