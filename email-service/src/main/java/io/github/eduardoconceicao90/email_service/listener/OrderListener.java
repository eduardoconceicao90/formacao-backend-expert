package io.github.eduardoconceicao90.email_service.listener;

import io.github.eduardoconceicao90.email_service.service.EmailService;
import io.github.eduardoconceicao90.email_service.util.UtilEmail;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class OrderListener {

    @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange(value = "helpdesk", type = "topic"),
                key = "rk.orders.create",
                value = @Queue(value = "queue.orders")
    ))
    public void listener(OrderCreatedMessage message) throws MessagingException, UnsupportedEncodingException {
        log.info("Order service processing: {}", message);
        String messageHtml = UtilEmail.messageOrderCreated(message);

        EmailService envioEmail = new EmailService(
                "eduardosaconceicao@gmail.com," + message.getRequester().email(),
                "Order Tech","Ordem de serviço", messageHtml
        );

        envioEmail.sendEmail();
    }

}
