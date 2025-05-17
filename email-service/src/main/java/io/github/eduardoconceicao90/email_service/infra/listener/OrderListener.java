package io.github.eduardoconceicao90.email_service.infra.listener;

import io.github.eduardoconceicao90.email_service.infra.util.EmailUtils;
import io.github.eduardoconceicao90.email_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
    public void listenerOrderCreated(OrderCreatedMessage order) throws Exception {
        log.info("Order service create processing: {}", order);
        var email = EmailUtils.contextCreatedOrder(order);
        emailService.sendEmail(email);
    }

}
