package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Service for sending emails.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    /**
     * Method for sending a verification email. The user's first name and OTP are used to create the email.
     * Template engine is used to process the email template.
     * @param user user
     */
    public void sendVerificationEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("otp", user.getOtp().getValue());
        String process = templateEngine.process("otpEmailTemplate", context);
        sendHtmlEmail(user.getEmail(), "Verifiziere deinen VinoVibes Account", process);
    }

    /**
     * Method for sending a forgot password email. The user's first name and token are used to create the email.
     * Template engine is used to process the email template.
     * @param user user
     */
    public void sendForgotPasswordEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("token", user.getToken().getValue());
        String process = templateEngine.process("forgotPasswordEmailTemplate", context);
        sendHtmlEmail(user.getEmail(), "Setze dein VinoVibes Passwort zurück", process);
    }

    /**
     * Method for sending an email with HTML content. The email is sent asynchronously.
     * If any of the parameters are null, an exception is thrown.
     * @param to email address
     * @param subject subject
     * @param htmlContent HTML content
     */
    @Async
    protected void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            Objects.requireNonNull(to, "Email address must not be null");
            Objects.requireNonNull(subject, "Subject must not be null");
            Objects.requireNonNull(htmlContent, "HTML content must not be null");

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(htmlContent, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("info@vino-vibes.com");
            mailSender.send(mimeMessage);
        } catch (NullPointerException e) {
            throw new AppException("Email sending failed due to null parameters", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException e) {
            throw new AppException("Could not send email due to messaging exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
