package io.github.eduardoconceicao90.order_service_api.service.impl;

import io.github.eduardoconceicao90.order_service_api.entity.Order;
import io.github.eduardoconceicao90.order_service_api.mapper.OrderMapper;
import io.github.eduardoconceicao90.order_service_api.repository.OrderRepository;
import io.github.eduardoconceicao90.order_service_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static models.enums.OrderStatusEnum.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }

    @Override
    public OrderResponse update(Long id, UpdateOrderRequest updateOrderRequest) {
        Order entity = findById(id);
        closedOrder(updateOrderRequest, entity);
        return orderMapper.fromEntity(orderRepository.save(orderMapper.update(updateOrderRequest, entity)));
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + OrderResponse.class.getSimpleName()
        ));
    }

    @Override
    public void closedOrder(UpdateOrderRequest updateOrderRequest, Order entity) {
        if(updateOrderRequest.status() != null && updateOrderRequest.status().equals(CLOSED.getDescription())) {
            entity.setClosedAt(LocalDateTime.now());
        }
    }

}
