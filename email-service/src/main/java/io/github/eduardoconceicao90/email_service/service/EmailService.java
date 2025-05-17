package io.github.eduardoconceicao90.email_service.service;

import io.github.eduardoconceicao90.email_service.util.UtilEmail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
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

    public void sendEmail(OrderCreatedMessage order) throws Exception {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);
        Address[] toUsers = InternetAddress.parse("eduardosaconceicao@gmail.com, " + order.getCustomer().email());

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Order Tech", "UTF-8")); // Remetente
        message.setSubject("Ordem de Serviço"); // Assunto
        message.setRecipients(Message.RecipientType.TO, toUsers); // Destinatário(s)
        message.setContent(UtilEmail.messageOrderCreated(order), "text/html; charset=utf-8"); // Corpo do email

        try {
            Transport.send(message);
            log.info("Email sending successfully.");
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }

}
