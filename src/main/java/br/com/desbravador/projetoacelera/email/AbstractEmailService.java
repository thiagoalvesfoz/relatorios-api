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

    @Value("${email.default.sender}")
    private String sender;

    @Value("${email.default.senderName}")
    private String senderName;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public final void sendAccountRegistration(User user) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromUser(user);
        sendEmail(sm);
    }

    @Override
    public final void sendHtmlAccountRegistration(User user) {
        try {
            MimeMessage mm = prepareMimeMessageFromUser(user);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            sendAccountRegistration(user);
        }
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

    protected MimeMessage prepareMimeMessageFromUser(User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(user.getEmail());
        mmh.setFrom(sender, senderName);
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

    @Override
    public void sendNewPasswordEmail(User user, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("New password request");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("New password: " + newPass);
        return sm;
    }

    @Override
    public final void sendHtmlNewPasswordEmail(User user, String newPass) {
        try {
            MimeMessage mm = prepareMimeMessageFromNewRequestPassword(user, newPass);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            sendNewPasswordEmail(user, newPass);
        }
    }

    protected MimeMessage prepareMimeMessageFromNewRequestPassword(User user, String newPass) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(user.getEmail());
        mmh.setFrom(sender, senderName);
        mmh.setSubject("New password request");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateRequestNewPassword(user, newPass), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateRequestNewPassword(User user, String newPass) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("newPass", newPass);
        return templateEngine.process("email/forgotPassword", context);
    }

    //    RESET PASSWORD EMAIL
    @Override
    public void sendResetPasswordEmail(String recipientEmail, String link) {
        SimpleMailMessage sm = prepareResetPasswordEmail(recipientEmail, link);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareResetPasswordEmail(String recipientEmail, String link) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(recipientEmail);
        sm.setFrom(sender);
        sm.setSubject("Here's the link to reset your password");
        sm.setSentDate(new Date(System.currentTimeMillis()));

        String content = "Hello, \n"
                + "You have requested to reset your password.\n"
                + "Click the link below to change your password: "
                + link
                + "\n"
                + "Ignore this email if you do remember your password, \n"
                + "or you have not made the request.";

        sm.setText(content);
        return sm;
    }

    @Override
    public final void sendHtmlResetPasswordEmail(String recipientEmail, String link) {
        try {
            MimeMessage mm = prepareMimeMessageFromResetPassword(recipientEmail, link);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            sendResetPasswordEmail(recipientEmail, link);
        }
    }

    protected MimeMessage prepareMimeMessageFromResetPassword(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(recipientEmail);
        mmh.setFrom(sender, senderName);
        mmh.setSubject("Here's the link to reset your password");
        mmh.setSentDate(new Date(System.currentTimeMillis()));

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        mmh.setText(content, true);
        return mimeMessage;
    }
}
