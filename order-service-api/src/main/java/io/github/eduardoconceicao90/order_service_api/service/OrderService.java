package io.github.eduardoconceicao90.order_service_api.service;

import models.requests.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest createOrderRequest);

}
