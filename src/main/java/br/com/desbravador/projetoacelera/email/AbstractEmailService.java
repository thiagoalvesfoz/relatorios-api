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
    public final void sendAccountRegistration(User account, String setPasswordLink) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromSetPassword(account, setPasswordLink);
        sendEmail(sm);
    }

    @Override
    public final void sendHtmlAccountRegistration(User account, String setPasswordLink) {
        try {
            MimeMessage mm = prepareMimeMessageFromSetPassword(account, setPasswordLink);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            sendAccountRegistration(account, setPasswordLink);
        }
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromSetPassword(User account, String setPasswordLink) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(account.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Please verify your registration");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        String content = "Hello "+ account.getName() +"! \n\n"
                + "Thanks for signing up for Desbravador Report!.\n\n"
                + "Here's what to do next to activate your account: \n\n"
                + " - Copy URL Link: " + setPasswordLink + "\n"
                + " - Paste on your browser. \n"
                + " - Add a password. \n\n"
                + "Please ignore this email if you didn't make the request. \n\n"
                + "Thanks,\n"
                + "Desbravador team\n";

        sm.setText(content);
        return sm;
    }

    protected MimeMessage prepareMimeMessageFromSetPassword(User account, String setPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(account.getEmail());
        mmh.setFrom(sender, senderName);
        mmh.setSubject("Please verify your registration");
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateRegistration(account, setPasswordLink), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateRegistration(User account, String setPasswordLink) {
        Context context = new Context();
        context.setVariable("user", account);
        context.setVariable("setPasswordLink", setPasswordLink);
        return templateEngine.process("email/registration", context);
    }

    //    RESET PASSWORD EMAIL
    @Override
    public void sendResetPasswordEmail(User user, String link) {
        SimpleMailMessage sm = prepareResetPasswordEmail(user, link);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareResetPasswordEmail(User user, String link) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
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
    public final void sendHtmlResetPasswordEmail(User user, String link) {
        try {
            MimeMessage mm = prepareMimeMessageFromResetPassword(user, link);
            sendHtmlEmail(mm);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            sendResetPasswordEmail(user, link);
        }
    }

    protected MimeMessage prepareMimeMessageFromResetPassword(User user, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(user.getEmail());
        mmh.setFrom(sender, senderName);
        mmh.setSubject("Here's the link to reset your password");
        mmh.setSentDate(new Date(System.currentTimeMillis()));

//        String content = "<p>Hello,</p>"
//                + "<p>You have requested to reset your password.</p>"
//                + "<p>Click the link below to change your password:</p>"
//                + "<p><a href=\"" + link + "\">Change my password</a></p>"
//                + "<br>"
//                + "<p>Ignore this email if you do remember your password, "
//                + "or you have not made the request.</p>";

        mmh.setText(htmlFromTemplateResetPassword(user, link), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateResetPassword(User user, String setPasswordLink) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("setPasswordLink", setPasswordLink);
        return templateEngine.process("email/forgot-password", context);
    }
}
