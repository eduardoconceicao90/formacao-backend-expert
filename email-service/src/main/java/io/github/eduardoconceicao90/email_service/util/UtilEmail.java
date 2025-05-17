package io.github.eduardoconceicao90.email_service.util;

import lombok.experimental.UtilityClass;
import models.dtos.OrderCreatedMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UtilEmail {

    public String messageOrderCreated(OrderCreatedMessage order){
        return  "Olá, " + order.getCustomer().name() + "<br/>" +
                "Sua ordem de serviço foi criada com sucesso!<br/><br/>" +

                "<b>Dados do cliente:</b><br/>" +
                "Nome: " + order.getCustomer().name() + "<br/>" +
                "E-mail: " + order.getCustomer().email() + "<br/><br/>" +

                "<b>Dados da ordem:</b><br/>" +
                "Título: " + order.getOrder().title() + "<br/>" +
                "Descrição: " + order.getOrder().description() + "<br/>" +
                "Status: " + order.getOrder().status() + "<br/>" +
                "Técnico reponsável: " + order.getRequester().name() + "<br/>" +
                "Data de criação: " + UtilDate.convertDate(order.getOrder().createdAt()) + "<br/><br/>";
    }

}
