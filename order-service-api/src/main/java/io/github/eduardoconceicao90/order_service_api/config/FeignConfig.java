package io.github.eduardoconceicao90.order_service_api.config;

import feign.codec.ErrorDecoder;
import io.github.eduardoconceicao90.order_service_api.decoder.RetrieveMessageErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new RetrieveMessageErrorDecoder();
    }

}
