package io.github.eduardoconceicao90.email_service.infra.util;

import io.github.eduardoconceicao90.email_service.dto.EmailRequest;
import lombok.experimental.UtilityClass;
import models.dtos.OrderCreatedMessage;

import java.util.Map;

@UtilityClass
public class EmailUtils {

    public EmailRequest contextCreatedOrder(OrderCreatedMessage order){
        var email = new EmailRequest(
                "eduardosaconceicao@gmail.com, " + order.getCustomer().email(),
                "Order Tech",
                "Ordem de serviço criada",
                "order-created"
        );

        ThymeleafUtils.addVariavelThymeleaf(Map.of(
                "customerName", order.getCustomer().name(),
                "orderTitle", order.getOrder().title(),
                "orderDescription", order.getOrder().description(),
                "orderStatus", order.getOrder().status(),
                "requesterName", order.getRequester().name(),
                "createdAt", DateUtils.convertDate(order.getOrder().createdAt())
        ));

        return email;
    }

}
