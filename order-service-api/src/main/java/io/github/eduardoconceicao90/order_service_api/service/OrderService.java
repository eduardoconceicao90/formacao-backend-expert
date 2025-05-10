package io.github.eduardoconceicao90.order_service_api.service;

import io.github.eduardoconceicao90.order_service_api.entity.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

public interface OrderService {

    Order findById(Long id);

    void closedOrder(UpdateOrderRequest updateOrderRequest, Order entity);

    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(Long id, UpdateOrderRequest updateOrderRequest);

}
