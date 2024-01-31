package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.constants.EmailTemplates;
import com.vinovibes.vinoapi.entities.User;
import com.vinovibes.vinoapi.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    // public void sendVerificationEmail(User user) {
    //     String template = EmailTemplates.OTP_EMAIL_TEMPLATE;
    //     String name = user.getFirstName();
    //     String otp = user.getOtp().getValue();
    //     template = template.replace("{{NAME}}", name).replace("{{OTP}}", otp);
    //     sendHtmlEmail(user, "Verifiziere deinen VinoVibes Account", template);
    // }

    public void sendVerificationEmail(User user) {
        
    }

    public void sendForgotPasswordEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@vino-vibes.com");
        message.setTo(user.getEmail());
        message.setSubject("Passwort zurücksetzen");
        message.setText(
            """
            Hey %s!

            Du hast dein Passwort vergessen? Kein Problem!

            Bitte klicke auf den folgenden Link, um dein Passwort zurückzusetzen:

            http://localhost:3000/forgot-password?token=%s

            Der Link ist bis zum %s um %s Uhr gültig.

            Dein VinoVibes-Team :)
            """.formatted(
                    user.getFirstName(),
                    user.getToken().getValue(),
                    user.getToken().getExpiryTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    user.getToken().getExpiryTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
        );
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new AppException("Could not send email", HttpStatus.BAD_REQUEST);
        }
    }

    @Async
    protected void sendHtmlEmail(User user, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(htmlContent, true); // Setze true, um anzuzeigen, dass es sich um HTML handelt
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setFrom("info@vino-vibes.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new AppException("Could not send email", HttpStatus.BAD_REQUEST);
        }
    }
}
