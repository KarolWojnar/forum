package org.forum.service;

import lombok.RequiredArgsConstructor;
import org.forum.exception.MailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.activation-link}")
    private String activationLink;

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String activationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject("Activate your account");
        message.setText("Your activation link is: " + activationLink + activationToken);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e);
        }
    }
}
