package io.github.eduardoconceicao90.order_service_api.service;

import io.github.eduardoconceicao90.order_service_api.entity.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Order findById(final Long id);

    void closedOrder(UpdateOrderRequest updateOrderRequest, Order entity);

    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(final Long id, UpdateOrderRequest updateOrderRequest);

    void deleteById(final Long id);

    List<OrderResponse> findAll();

    Page<OrderResponse> findAllPaginated(final Integer page, final Integer size, final String direction, final String sort);

}
