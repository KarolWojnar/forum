package org.forum.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.forum.exception.MailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject("Activate your account");
            String htmlContent = """
                <html>
                <body>
                    <h2 style="color: #007bff;">Welcome to our forum!</h2>
                    <p>Click the button below to activate your account:</p>
                    <p><a href='%s%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
                     color: white; text-decoration: none; border-radius: 5px;'>Activate Account</a></p>
                    <p>If the button doesn't work, use the following link:</p>
                    <p><a href='%s%s'>%s%s</a></p>
                    <p>Best regards,<br>Forum Team</p>
                </body>
                </html>
            """.formatted(activationLink, activationToken, activationLink, activationToken, activationLink, activationToken);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e);
        }
    }
}
