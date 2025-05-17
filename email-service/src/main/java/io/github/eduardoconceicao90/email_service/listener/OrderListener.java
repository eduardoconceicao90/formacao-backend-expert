package io.github.eduardoconceicao90.email_service.listener;

import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange(value = "helpdesk", type = "topic"),
                key = "rk.orders.create",
                value = @Queue(value = "queue.orders", durable = "false")
    ))
    public void listener(OrderCreatedMessage message) {
        log.info("Order service processing: {}", message);
    }

}
