package com.vinovibes.vinoapi.services;

import com.vinovibes.vinoapi.entities.User;
import com.vinovibes.vinoapi.exceptions.AppException;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

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
                        """.formatted(user.getFirstName(), user.getOtp().getToken(),
                        user.getOtp().getExpiryTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        user.getOtp().getExpiryTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new AppException("Could not send email", HttpStatus.BAD_REQUEST);
        }
    }
}
