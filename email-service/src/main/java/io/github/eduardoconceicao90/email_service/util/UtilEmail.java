package io.github.eduardoconceicao90.email_service.util;

import lombok.experimental.UtilityClass;
import models.dtos.OrderCreatedMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UtilEmail {

    public static String USERNAME = "eduardodevjavaweb@gmail.com";
    public static String PASSWORD = "nxev ubuc bxfa ikmv";

    public String messageOrderCreated(OrderCreatedMessage message){
        return  "Olá, " + message.getCustomer().name() + "<br/>" +
                "Sua ordem de serviço foi criada com sucesso!<br/><br/>" +

                "<b>Dados do cliente:</b><br/>" +
                "Nome: " + message.getCustomer().name() + "<br/>" +
                "E-mail: " + message.getCustomer().email() + "<br/><br/>" +

                "<b>Dados da ordem:</b><br/>" +
                "Título: " + message.getOrder().title() + "<br/>" +
                "Descrição: " + message.getOrder().description() + "<br/>" +
                "Status: " + message.getOrder().status() + "<br/>" +
                "Técnico reponsável: " + message.getRequester().name() + "<br/>" +
                "Data de criação: " + UtilDate.convertDate(message.getOrder().createdAt()) + "<br/><br/>";
    }

}
