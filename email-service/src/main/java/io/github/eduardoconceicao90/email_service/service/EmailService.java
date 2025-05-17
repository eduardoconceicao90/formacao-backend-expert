package io.github.eduardoconceicao90.email_service.service;

import io.github.eduardoconceicao90.email_service.dto.EmailRequest;
import io.github.eduardoconceicao90.email_service.util.ThymeleafUtils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    private final Properties mailProperties;

    public void sendEmail(final EmailRequest email) throws Exception {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);
        Address[] toUsers = InternetAddress.parse(email.listaDestinatarios());

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, email.nomeRemetente(), "UTF-8")); // Remetente
        message.setSubject(email.assuntoEmail()); // Assunto
        message.setRecipients(Message.RecipientType.TO, toUsers); // Destinatário(s)
        String emailBody = ThymeleafUtils.processarTemplateThymeleaf(email.templatePath()); // Processa o template
        message.setContent(emailBody, "text/html; charset=utf-8"); // Corpo do email

        try {
            Transport.send(message);
            log.info("Email sending successfully.");
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }

}
