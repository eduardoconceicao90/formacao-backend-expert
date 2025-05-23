package io.gitbhub.eduardoconceicao90.helpdesk_bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HelpdeskBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskBffApplication.class, args);
	}

}
