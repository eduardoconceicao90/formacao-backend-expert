package io.github.eduardoconceicao90.email_service.service;

import io.github.eduardoconceicao90.email_service.util.UtilEmail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Slf4j
@Service
public class EmailService {




    public void sendEmail(OrderCreatedMessage order) throws Exception {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            }
        });

        session.setDebug(true);
        Address[] toUsers = InternetAddress.parse("eduardosaconceicao@gmail.com," + order.getCustomer().email());

        Message message = new MimeMessage(session);

        try {
            Transport.send(message);
        }
    }

}
