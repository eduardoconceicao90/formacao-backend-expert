package io.github.eduardoconceicao90.email_service.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UtilDate {

    public String convertDate(String date) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
                LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        );
    }

}
