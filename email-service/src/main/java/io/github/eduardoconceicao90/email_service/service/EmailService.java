package io.github.eduardoconceicao90.email_service.service;

import io.github.eduardoconceicao90.email_service.util.UtilEmail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EmailService {

    private String listaDestinatarios, nomeRemetente, assuntoEmail, msgEmail;

    public void sendEmail() throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(UtilEmail.USERNAME, UtilEmail.PASSWORD);
            }

        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(listaDestinatarios);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(UtilEmail.USERNAME, nomeRemetente, "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assuntoEmail);
        message.setContent(msgEmail, "text/html; charset=utf-8");

        try {
            Transport.send(message);
            log.info("Email enviado com sucesso para: {}", listaDestinatarios);
        } catch (MessagingException e) {
            log.error("Erro ao enviar o email: {}", e.getMessage());
        }
    }

}
