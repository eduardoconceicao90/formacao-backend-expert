package io.gitbhub.eduardoconceicao90.helpdesk_bff.config;

import feign.codec.ErrorDecoder;
import io.gitbhub.eduardoconceicao90.helpdesk_bff.decoder.RetrieveMessageErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder getDecoder() {
        return new RetrieveMessageErrorDecoder();
    }

}
