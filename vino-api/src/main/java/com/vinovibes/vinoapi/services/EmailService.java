package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.exceptions.AppException;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@vinovibes.com");
        message.setTo(user.getEmail());
        message.setSubject("Verifiziere deinen VinoVibes Account");
        message.setText(
            """
            Hey %s!

            Bitte verifiziere deinen Account.
            Dein persönlicher Code:

            %s

            Der Code ist bis zum %s um %s Uhr gültig.

            Dein VinoVibes-Team :)
            """.formatted(
                    user.getFirstName(),
                    user.getOtp().getValue(),
                    user.getOtp().getExpiryTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    user.getOtp().getExpiryTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
        );
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new AppException("Could not send email", HttpStatus.BAD_REQUEST);
        }
    }

    @Async
    public void sendForgotPasswordEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@vinovibes.com");
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
}
