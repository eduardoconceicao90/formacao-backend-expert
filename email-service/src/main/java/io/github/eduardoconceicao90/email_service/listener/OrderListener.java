package io.github.eduardoconceicao90.email_service.listener;

import io.github.eduardoconceicao90.email_service.service.EmailService;
import io.github.eduardoconceicao90.email_service.util.UtilEmail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderListener {

    private final EmailService emailService;

    @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange(value = "helpdesk", type = "topic"),
                key = "rk.orders.create",
                value = @Queue(value = "queue.orders")
    ))
    public void listener(OrderCreatedMessage order) throws Exception {
        log.info("Order service processing: {}", order);
        emailService.sendEmail(order);
    }

}
