package io.github.eduardoconceicao90.order_service_api.decoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import models.exceptions.GenericFeignException;

import java.io.InputStream;
import java.util.Map;

@Slf4j
public class RetrieveMessageErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            final var error = mapper.readValue(bodyIs, Map.class);
            final var status = (Integer) error.get("status");
            log.error("Error in Feign Client: {} - {}", methodKey, error);
            return new GenericFeignException(status, error);
        } catch (Exception e) {
            return new Exception("Error reading response body", e);
        }
    }

}
